package com.github.lawdcoder.loaner.repository;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import org.springframework.stereotype.Repository;
import com.github.lawdcoder.loaner.domain.clientInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

@Repository
public class clientRepository {
    private CqlSession session;
    //private static final Logger log =
          //  (Logger) LoggerFactory.getLogger("cass.client.repo");

    public clientRepository(CqlSession session) {
        this.session = session;
    }




    public clientInfo create(clientInfo clientInfo){
       // log.info("Adding a client.");
        SimpleStatement statement = SimpleStatement.builder(
                        "INSERT INTO market.investors (clientId, name, creditrating,annualIncome,loantype, loanstatus, loanapprove) values (?,?,?,?,?,?,?)")
                .addPositionalValues(
                        clientInfo.getClientId(),
                        clientInfo.getAnnualIncome(),
                        clientInfo.getName(),
                        clientInfo.getCreditrating(),
                        clientInfo.getLaonapproval(),
                        clientInfo.getLoantype(),
                        clientInfo.getLoanstatus())
                .build();
        Flux.from(session.executeReactive(statement)).subscribe();
        return clientInfo;
    }





    public Flux<clientInfo> getAll() {
       // log.info("get.");
        return Flux.from(session.executeReactive("SELECT * FROM loaner.client"))
                .map(row -> new clientInfo(row.getInt("clientId"), row.getString("name"), row.getInt("creditrating"),
                        row.getInt("annualIncome"), row.getString("loantype"), row.getString("loanstatus"),row.getDouble("loanapproval")));
    }

    public Mono<clientInfo> get(int Clientid) {
       // log.info("pulling.");
        return Mono.from(session.executeReactive("SELECT * FROM loaner.client WHERE clientId = " + Clientid))
                .map(row -> new clientInfo(row.getInt("clientId"), row.getString("name"), row.getInt("creditrating"),
                        row.getInt("annualIncome"), row.getString("loantype"),
                        row.getString("loanstatus"),row.getDouble("loanapproval")));
    }

    public Mono<Integer> deleteClient(int clientid){
      //  log.info(".");
        Flux.from(
                session.executeReactive(
                        SimpleStatement.builder("DELETE FROM loaner.client WHERE clientId = ?")
                                .addPositionalValue(clientid)
                                .build()
                )
        ).subscribe();
        return Mono.just(clientid);
    }

    public Double updateClient(int clientid, Double loanapprove) {
       // log.info("Updating Client loan.");

        Update update = QueryBuilder.update("loaner", "client")
                .setColumn("loanapprove", QueryBuilder.bindMarker())
                .whereColumn("clientid")
                .isEqualTo(QueryBuilder.bindMarker());

        Mono.from(
                        session.executeReactive(
                                session.prepare(update.build()).bind().setDouble(0, loanapprove).setInt(1, clientid)))
                .subscribe();
        return loanapprove;
    }



}
