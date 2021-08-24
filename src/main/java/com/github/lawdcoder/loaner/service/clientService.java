package com.github.lawdcoder.loaner.service;
import com.github.lawdcoder.loaner.domain.clientInfo;
import com.github.lawdcoder.loaner.repository.clientRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

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
    public Mono<clientInfo> get( String clientid)
    {return clientRepository.get(Integer.parseInt(clientid));}

    public clientInfo create(clientInfo clientinfo)
    {return clientRepository.create(clientinfo);}

    public Double updateClient(Map<String, String> params){
        return clientRepository.updateClient(Integer.parseInt(params.get("clientId")),
                Double.parseDouble(params.get("loan_approval")));
    }
}
