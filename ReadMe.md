# Rewards System


## Problem Statement

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase as follows:

For every dollar spent over $50 on the transaction, the customer receives one point.

In addition, for every dollar spent over $100, the customer receives another point.

Ex: for a $120 purchase, the customer receives

`(120 - 50) x 1 + (120 - 100) x 1 = 90 points`


Given a record of every transaction during a three-month period, calculate the reward points earned for each customer per month and total. 




## Introduction

- Java JDK-1.8 
- Spring Boot Embeded Tomcat Start local server
- Spring Data JPA
- H2 DB


API's

- Import the code as Maven project
- Maven Build - mvn clean install
- Start local server : mvn run
- Swagger URL- http://localhost:8080/swagger-ui/index.html#/
- Create New Customer Purchase - http://localhost:8080/customer
- Get Customers Rewards  - http://localhost:8080/customer/{customerId}
