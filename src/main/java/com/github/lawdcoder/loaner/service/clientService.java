package com.github.lawdcoder.loaner.service;
import com.github.lawdcoder.loaner.domain.clientInfo;
import com.github.lawdcoder.loaner.repository.clientRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class clientService {
    private clientRepository clientRepository;

    public clientService(clientRepository repository)
    {
        this.clientRepository=repository;
    }
    public Flux<clientInfo> getAll()
    {
        return clientRepository.getAll();
    }
    public Mono<clientInfo> get( String client_id)
    {return clientRepository.get(Integer.parseInt(client_id));}

    public clientInfo create(clientInfo clientinfo)
    {return clientRepository.create(clientinfo);}
}
