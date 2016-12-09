drop table if exists accounts;

create table accounts (
  id integer primary key,
  acctname varchar(50),
  balance integer,
  ssn varchar(9),
  phone varchar(10),
  addr varchar(50),
  state varchar(50)
);

INSERT INTO accounts (id, acctname, balance,ssn,phone,addr,state) VALUES (223455,'Christina Lin', 10000,'749980000','6364858533','4 BLACK DEER ST, STONE','TX');