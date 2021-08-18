package com.github.lawdcoder.loaner.repository;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import org.springframework.stereotype.Repository;
import com.github.lawdcoder.loaner.domain.clientInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public class repository {
    private CqlSession session;

    public repository(CqlSession session) {
        this.session = session;
    }

    public Flux<clientInfo> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM loaner.clients"))
        .map(row-> new clientInfo(row.getInt("client_id"), row.getString( "fName" ), row.getString( "lName")
        ,row.getString("addr"), row.getInt("ssn"), row.getString("appdate"),
         row.getString("dob"), row.getDouble("credit_rating"),
        row.getDouble("annual_income"), row.getString( "loan_type")));
    }
    public Mono<clientInfo> get(int client_id)
    {
        return Mono.from(session.executeReactive("SELECT * FROM loaner.clients WHERE client_id="+ client_id))
                .map(row-> new clientInfo(row.getInt("client_id"), row.getString( "fName" ), row.getString( "lName")
                        ,row.getString("addr"), row.getInt("ssn"), row.getString("appdate"),
                        row.getString("dob"), row.getDouble("credit_rating"),
                        row.getDouble("annual_income"), row.getString( "loan_type")));
    }
    public clientInfo create(clientInfo clientinfo)
    {
        SimpleStatement stm =SimpleStatement.builder("INSERT INTO loaner.clients" +
                "(client_id,fName,lName,addr,ssn,appdate,dob,credit_rating,annual_income,loan_type) values(?, ?, ?)")
                .addPositionalValues(clientinfo.getClient_id(), clientinfo.getfName(),clientinfo.getlName(), clientinfo.getAddr(),
                        clientinfo.getssn(), clientinfo.getAppdate(),clientinfo.getDob(), clientinfo.getCredit_rating(),
                        clientinfo.getAnnual_income(),clientinfo.getLoan_type()).build();
        Flux.from(session.executeReactive(stm)).subscribe();
        return clientinfo;
    }
}
