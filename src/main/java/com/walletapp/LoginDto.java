package com.walletapp;

public class LoginDto {
    Integer id;
    String gmail;
    String password;

    public LoginDto() {
    }

    public LoginDto(Integer id, String gmail, String password) {
        this.id = id;
        this.gmail = gmail;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
