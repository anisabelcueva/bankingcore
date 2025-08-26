CREATE DATABASE BANKINGCOREDB;

USE BANKINGCOREDB;

CREATE TABLE CUSTOMER (
customer_id BIGINT NOT NULL AUTO_INCREMENT,
firssecondname VARCHAR(50),
lastname VARCHAR(50),
dni VARCHAR(8),
email VARCHAR(80),
UNIQUE (dni),
CONSTRAINT customer_customerid_pk PRIMARY KEY(customer_id)   
);

CREATE TABLE ACCOUNTS (
account_id BIGINT NOT NULL AUTO_INCREMENT,
account_number BIGINT,
balance DECIMAL(6, 2),
account_type VARCHAR(20),
account_status INT,
customer_id_fk BIGINT,
CONSTRAINT account_id_pk PRIMARY KEY(account_id),
CONSTRAINT account_customerid_fk FOREIGN KEY(customer_id_fk) REFERENCES customer(customer_id)
);

CREATE TABLE TRANSACTIONS (
transaction_id BIGINT NOT NULL AUTO_INCREMENT,
creation_date  DATETIME NOT NULL DEFAULT NOW(),
transaction_type VARCHAR(20),
amount DECIMAL(6, 2),
account_id_fk BIGINT,
CONSTRAINT transaction_id_pk PRIMARY KEY(transaction_id),
CONSTRAINT account_transactionid_fk FOREIGN KEY(account_id_fk) REFERENCES accounts(account_id)
);


INSERT INTO CUSTOMER (customer_id, firssecondname, lastname, dni, email) VALUES (1, "Ana Isabel", "Cueva Castillo", "10202238", "anisabelcueva@gmail.com");
INSERT INTO ACCOUNTS (account_id, account_number, balance, account_type, account_status, customer_id_fk) VALUES (1,5760223017905676385, 1500.30,"SAVINGS_ACCOUNT",1,1); 



Select * From ACCOUNTS;
Select * From CUSTOMER;
Select * From TRANSACTIONS;




