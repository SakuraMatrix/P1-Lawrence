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

@ComponentScan
@Configuration
public class entryPointConfig {
    @Autowired
    clientService clientservice;
    @Bean
    public CqlSession session(){return CqlSession.builder().build();}

    @Bean
    public DisposableServer server() throws URISyntaxException {
        Path indexHTML = Paths.get(entrypoint.class.getResource("/index.html").toURI());
        Path errorHTML = Paths.get(entrypoint.class.getResource("/error.html").toURI());

        CqlSession session = CqlSession.builder().build();
        repository repository = new repository(session);
        clientService clientservice = new clientService(repository);

        HttpServer.create()
                .port(8080)
                .route(routes ->
                        routes.get("/clients", (request, response) ->
                                        response.send(clientservice.getAll().map(entrypoint::toByteBuf)
                                                .log("http-server")))
                                .get("/clients/{param}", (request, response) ->
                                        response.send(clientservice.get(request.param("param")).map(entrypoint::toByteBuf)
                                                .log("http-server")))
                                .get("/", (request, response) ->
                                        response.sendFile(indexHTML))
                                .get("/error", (request, response) ->
                                        response.status(404).addHeader("Message", "Goofed")
                                                .sendFile(errorHTML))
                )
                .bindNow()
                .onDispose()
                .block();
    }

    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static ByteBuf toByteBuf(Object o) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            OBJECT_MAPPER.writeValue(out, o);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());
    }
}

}
