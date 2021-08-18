package com.github.lawdcoder.loaner.service;
import com.github.lawdcoder.loaner.domain.clientInfo;
import com.github.lawdcoder.loaner.repository.repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class clientService {
    private repository repository1;

    public clientService(repository repository1)
    {
        this.repository1=repository1;
    }
    public Flux<clientInfo> getAll()
    {
        return repository1.getAll();
    }
    public Mono<clientInfo> get( String client_id)
    {return repository1.get(Integer.parseInt(client_id));}

    public clientInfo create(clientInfo clientinfo)
    {return repository1.create(clientinfo);}
}
