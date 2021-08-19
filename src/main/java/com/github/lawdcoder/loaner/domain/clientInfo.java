package com.github.lawdcoder.loaner.domain;
import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

public class clientInfo {
    private String fName;
    private String lName;
    private String addr;
    private int ssn;
    private String appdate;
    private int client_id;
    private String dob;
    private double credit_rating;
    private double annual_income ;
    private String loan_type;
    private String loan_status;
    private double laon_approval;

    public clientInfo()
    {
    }
    public clientInfo(int client_id, String fName, String lName, String addr, int ssn, String appdate,String dob,double
            credit_rating, double annual_income,String loan_type, String loan_status, double loan_approval)
    {
        this.addr=addr;
        this.appdate=appdate;
        this.fName=fName;
        this.lName=lName;
        this.ssn=ssn;
        this.annual_income=annual_income;
        this.loan_type=loan_type;
        this.dob=dob;
        this.credit_rating=credit_rating;
        this.client_id=client_id;
    }



    @Override
    public String toString()
    {
        return "clientInfo{" +"addr="+addr+",appdate="+appdate+",client_id="+client_id+", fName="+fName +", lName="+
                lName+", ssn="+ssn+",annual_income= "+annual_income+",loan_type=" +loan_type+",dob="+dob+",credit_rating="
                + credit_rating+",loan_status="+loan_status+ ",loan_approval="+laon_approval+ '}';
    }

    @Override
    public boolean equals(Object ob)
    {
        if (this==ob)
            return true;
        if(ob==null || getClass() != ob.getClass())
            return false;
        clientInfo clientInfo=(clientInfo) ob;
        return client_id==clientInfo.client_id && Double.compare(clientInfo.credit_rating,credit_rating) == 0
                && Double.compare(clientInfo.annual_income, annual_income)==0
                && Objects.equals(dob,clientInfo.dob)
           && Objects.equals(loan_type,clientInfo.loan_type)
        && Objects.equals(ssn, clientInfo.ssn)
        && Objects.equals(lName, clientInfo.lName)
        && Objects.equals(fName, clientInfo.fName)
        && Objects.equals(appdate, clientInfo.appdate)
        && Objects.equals(addr, clientInfo.addr);




    }
    @Override
    public int hashCode() {
        return Objects.hash(client_id,credit_rating,addr
                ,appdate,annual_income,fName,lName,loan_type,dob,laon_approval,loan_status);
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setssn(int ssn) {
        this.ssn = ssn;
    }

    public void setAppdate(String appdate) {
        this.appdate = appdate;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setAnnual_income(double annual_income) {
        this.annual_income = annual_income;
    }

    public void setCredit_rating(double credit_rating) {
        this.credit_rating = credit_rating;
    }

    public void setLoan_type(String loan_type) {
        this.loan_type = loan_type;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getlName() {
        return lName;
    }

    public String getfName() {
        return fName;
    }

    public int getssn() {
        return ssn;
    }

    public String getAddr() {
        return addr;
    }

    public String getAppdate() {
        return appdate;
    }

    public String getDob() {
        return dob;
    }

    public double getAnnual_income() {
        return annual_income;
    }

    public int getClient_id() {
        return client_id;
    }

    public double getCredit_rating() {
        return credit_rating;
    }

    public String getLoan_type() {
        return loan_type;
    }

    public void setLaon_approval(double laon_approval) {
        if(getLoan_status()=="Approved")
        this.laon_approval = laon_approval;
        else this.laon_approval= 0;
    }

    public void setLoan_status(String loan_status) {
        if(getCredit_rating()<=6 || getAnnual_income()<=30000 && getLoan_type()=="Car"|| getLoan_type()=="Home")
            loan_status="Not Approved";
        else
            loan_status="Approved";
        this.loan_status = loan_status;
    }

    public double getLaon_approval() {
        return laon_approval;
    }

    public String getLoan_status() {
        return loan_status;
    }
}
