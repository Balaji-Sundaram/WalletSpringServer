package com.walletapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
		Hi this is Balaji Sundaram and this application is use for Money transaction and perform CRUD operation and
		  it can  add your funds in your wallet and withdraw funds from your wallet also you can transfer the fund to your friends
		  with a good secured app this app is  case-sensitive
		  The test cases were first written for normal repository then it were modified to the jpa repository
        DATABASE:
        USERNAME   & PASSWORD  : wallet
        NOTE:   the port is used in this file is 8080
        				if it shows error change to 8090
		  by
		  balaji --------------------------------->HappyCoding😊
 */
@SpringBootApplication
public class WalletAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(WalletAppApplication.class, args);
	}
}
