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
            int client_id = Integer.parseInt(params[0].split("=")[1]);
            String lName = params[1].split("=")[1];
            String fName = params[2].split("=")[1];

               String addr = params[3].split("=")[1];
               int ssn= Integer.parseInt(params[4].split("=")[1]);
               String appdate = params[5].split("=")[1];
               String dob = params[6].split("=")[1];
               String loan_type= params[7].split("=")[1];
               double credit_rating = Double.parseDouble(params[8].split("=")[1]);
               double annual_income= Double.parseDouble(params[9].split("=")[1]);
               double loan_approval=Double.parseDouble(params[10].split("=")[1]);
               String loan_status=params[11].split("=")[1];
               clientinfo=new clientInfo(client_id,fName,lName,addr,ssn,appdate,dob,
                       credit_rating,annual_income,loan_type,loan_status,loan_approval);
           }
           return clientinfo;
          }
 }
