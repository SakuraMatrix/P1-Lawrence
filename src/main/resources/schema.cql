CREATE KEYSPACE IF NOT EXIST loans  WITH replication={'class':'simple strategy', 'replication_factor': 1};
use loans;
CREATE TABLE IF NOT EXIST client(
client_id int,
first_name text,
last_name text,
address text,
dob timestamp,
application_date timestamp,
credit_rating decimal,
annual_income double,
ssn UUID,
loan_type test,
PRIMARY KEY(client_id,last_name)
);