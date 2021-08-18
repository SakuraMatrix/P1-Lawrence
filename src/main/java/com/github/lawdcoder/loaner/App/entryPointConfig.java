package com.github.lawdcoder.loaner.App;

import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lawdcoder.loaner.repository.repository;
import com.github.lawdcoder.loaner.service.clientService;
import io.netty.buffer.ByteBuf;
import com.github.lawdcoder.loaner.App.entrypoint;
import io.netty.buffer.ByteBufAllocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ComponentScan
public class entryPointConfig {
    @Autowired
    clientService clientservice;
    @Bean
    public CqlSession session(){return CqlSession.builder().build();}
    @Bean
    public DisposableServer server() throws URISyntaxException
    {
        Path indexHTML =Paths.get(
                entrypoint.class.getResource("/index.html").toURI());
        Path errorHTML = Paths.get(entrypoint.class.getResource("error.html").toURI());


        return HttpServer.create()
                .port(8080)
                .route(routes->
                        routes.get("/clients",(request,response)->
                                response.send(clientservice.getAll()
                                        .map(entrypoint::toByteBuf)
                                        .log("http-server")))
                                .post("clients", (request, response)->
                                        response.send(request.receive().asString()
                                                .map(entrypoint::parseClientInfo)
                                                .map(clientservice::create)
                                                .map(entrypoint::toByteBuf)
                                                .log("http-server")))
                                .get("/clients/{param}", (request, response)->
                                        response.send(clientservice.get(request.param("param"))
                                                .map(entrypoint::toByteBuf)
                                                .log("http-server")))
                                .get("/", (request,response)->
                                        response.sendFile(indexHTML))
                                .get("/error", (request,response)->
                                        response.status(404).addHeader("Message","Goofed")
                                                .sendFile(errorHTML))
                )
                .bindNow();

    }
}