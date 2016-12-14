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

INSERT INTO accounts (id, acctname, balance,ssn,phone,addr,state) VALUES (223455,'Chris L', 10000,'749980000','6364858533','4 BLACK DEER ST, STONE','TX');
INSERT INTO accounts (id, acctname, balance,ssn,phone,addr,state) VALUES (123456,'Simon C', 5000,'987655663','7264947276','43 SLIVER EAGLE ST, RIVER','MA');
INSERT INTO accounts (id, acctname, balance,ssn,phone,addr,state) VALUES (234567,'Amber K', 700,'424676543','4274558382','67 RED LION ST, ROCK','NY');
INSERT INTO accounts (id, acctname, balance,ssn,phone,addr,state) VALUES (345678,'Kelly J', 4400,'987676543','453088083','8 GREEN SHARK ST, MOUNTAIN','CA');