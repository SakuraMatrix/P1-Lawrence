package com.github.lawdcoder.loaner.domain;
import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

public class clientInfo {
    private String name;
    private int clientId;

    private double creditrating;
    private double annualIncome ;
    private String loantype;
    private String loanstatus;
    private double laonapproval;

    public clientInfo()
    {
    }
    public clientInfo(int clientIid, String name,
            int creditrating, int annualincome,String loantype, String loanstatus, double loanapproval)
    {

        this.name=name;
        this.annualIncome= annualIncome;
        this.creditrating=creditrating;
        this.clientId=clientIid;
        this.loanstatus=loanstatus;
        this.loantype=loantype;
        this.laonapproval=loanapproval;
    }



    @Override
    public boolean equals(Object ob)
    {
        if (this==ob)
            return true;
        if(ob==null || getClass() != ob.getClass())
            return false;
        clientInfo clientInfo=(clientInfo) ob;
        return clientId==clientInfo.clientId
                && Double.compare(clientInfo.annualIncome, annualIncome)==0
                && Double.compare(clientInfo.creditrating,creditrating) == 0
                && Double.compare(clientInfo.laonapproval, laonapproval)==0
                && Objects.equals(loanstatus,clientInfo.loanstatus)
                && Objects.equals(loantype,clientInfo.loantype)
        && Objects.equals(name, clientInfo.name);




    }
    @Override
    public int hashCode() {
        return Objects.hash(clientId,creditrating
                ,annualIncome,name,loantype,laonapproval,loanstatus);
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public double getCreditrating() {
        return creditrating;
    }

    public int getClientId() {
        return clientId;
    }

    public double getLaonapproval() {
        return laonapproval;
    }

    public String getLoanstatus() {
        return loanstatus;
    }

    public String getLoantype() {
        return loantype;
    }

    public String getName() {
        return name;
    }

    public void setAnnualIncome(double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setCreditrating(double creditrating) {
        this.creditrating = creditrating;
    }





    public void setName(String name) {
        this.name = name;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public void setLaonapproval(double laon_approval) {
        if(getLoanstatus()=="Approved")
        this.laonapproval = laonapproval;
        else this.laonapproval= 0;
    }

    public void setLoanstatus(String loan_status) {
        if(getCreditrating()<=600 || getAnnualIncome()<=30000 && getLoantype()=="Car"|| getLoantype()=="Home")
            loanstatus="Not Approved";
        else
            loanstatus="Approved";
        this.loanstatus = loanstatus;
    }

    public double getLaon_approval() {
        return laonapproval;
    }

    public String getLoan_status() {
        return loanstatus;
    }
}
