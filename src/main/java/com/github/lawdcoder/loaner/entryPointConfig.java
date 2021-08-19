package com.github.lawdcoder.loaner;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.lawdcoder.loaner.service.clientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ComponentScan
public class entryPointConfig {
    @Autowired
    clientService clientService;
    @Bean
    public CqlSession session()
    {return CqlSession.builder().build();}
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
                                response.send(clientService.getAll()
                                        .map(entrypoint::toByteBuf)
                                        .log("http-server")))
                                .post("clients", (request, response)->
                                        response.send(request.receive().asString()
                                                .map(entrypoint::parseClientInfo)
                                                .map(clientService::create)
                                                .map(entrypoint::toByteBuf)
                                                .log("http-server")))
                                .get("/clients/{param}", (request, response)->
                                        response.send(clientService.get(request.param("param"))
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