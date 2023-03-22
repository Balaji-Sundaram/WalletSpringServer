package com.walletapp;

import org.springframework.context.annotation.PropertySource;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
@Entity
@PropertySource("classpath:application.properties")
public class WalletDto { // POJO
    @Id
    @NotNull(message = "Id Not be Null")
    private Integer id;
    private String name;
    private Double balance;
    private String eMail ;
    @Pattern(regexp = "[a-zA-Z1-9]{8}",message = "Your Password Must be 8 and not more or less and it must contain lower case and upper case and numbers !!!")
    private String password;
    private LocalDate createdDate;
    private Integer fundTransferPin;
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public WalletDto() {
    }

    public WalletDto(Integer id, String name, Double balance, String eMail, String password, LocalDate createdDate,Integer fundTransferPin) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.eMail = eMail;
        this.password = password;
        this.createdDate = createdDate;
        this.fundTransferPin = fundTransferPin;
    }

    public Integer getFundTransferPin() {
        return fundTransferPin;
    }

    public void setFundTransferPin(Integer fundTransferPin) {
        this.fundTransferPin = fundTransferPin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "WalletDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", eMail='" + eMail + '\'' +
                ", password='" + password + '\'' +
                ", createdDate=" + createdDate +
                ", fundTransferPin=" + fundTransferPin +
                '}';
    }




}
