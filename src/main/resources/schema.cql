CREATE KEYSPACE IF NOT EXISTS loaner  WITH replication={'class':'SimpleStrategy', 'replication_factor': 1};
use loaner;
CREATE TABLE client
(clientId int Primary Key,
 name text,
 creditrating int,
 annualincome int,
 loantype text,
 loanapprove double,
 loanstatus text);


 INSERT into loaner.client (clientId,name,creditrating,annualincome, loantype, loanapprove,loanstatus)
 VALUES(1, 'text', 450, 20000, 'Home', 0.0, 'Approved,);