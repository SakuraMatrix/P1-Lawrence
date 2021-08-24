package com.github.lawdcoder.loaner;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lawdcoder.loaner.domain.clientInfo;
import com.github.lawdcoder.loaner.entryPointConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.netty.DisposableServer;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;


public class entrypoint {
  static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

          public static void main(String[] args) throws URISyntaxException
          {
           AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(entryPointConfig.class);

           applicationContext.getBean(DisposableServer.class)
                   .onDispose()
                   .block();
          }
          static ByteBuf toByteBuf(Object o) {
           ByteArrayOutputStream out = new ByteArrayOutputStream();
           try {
            OBJECT_MAPPER.writeValue(out, o);
           } catch (IOException e) {
            e.printStackTrace();
           }
           return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());
          }

          static clientInfo parseClientInfo(String str)
          {
           clientInfo clientinfo=null;
           try{
            clientinfo =OBJECT_MAPPER.readValue(str, clientInfo.class);
           }catch (JsonProcessingException e)
           {
            String[] params =str.split("&");
            int clientId = Integer.parseInt(params[0].split("=")[1]);
            String name = params[1].split("=")[1];
               String loantype= params[4].split("=")[1];
               int creditrating = Integer.parseInt(params[2].split("=")[1]);
               int annualIncome= Integer.parseInt(params[3].split("=")[1]);
               double loanapproval=0.0;
                      // Double.parseDouble(params[6].split("=")[1]);
               String loanstatus="";

                      //params[5].split("=")[1];

               clientinfo=new clientInfo(clientId,name,
                       creditrating,annualIncome,loantype,loanstatus,loanapproval);
           }
           return clientinfo;
          }
 }
