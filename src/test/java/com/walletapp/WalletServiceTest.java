package com.walletapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class WalletServiceTest {
    @Autowired
    private WalletService walletService;

    @BeforeEach
    public void init() throws WalletException {
        LocalDate date = LocalDate.now();
        walletService.registerWallet(new WalletDto(125567,"james",1200.0,"james@gmail.com","James123",date,1001));
    }
    @Test
    public void getWallet()throws WalletException{
        assertEquals("james",walletService.getWalletById(125567,"james@gmail.com","James123").getName());
    }
    @Test
    public void updateWallet()throws WalletException{
        LocalDate da = LocalDate.now();
        WalletDto walletDto=new WalletDto(125567,"Maven",1200.0,"Maven@gmail.com","Maven123",da,1002);
        walletService.updateWallet("james@gmail.com","James123", walletDto);  //first update
        assertEquals("Maven",walletService.getWalletById(125567,"Maven@gmail.com","Maven123").getName());  //check with new data
    }
    @Test
    public void updateWalletTestThrows(){
        LocalDate da = LocalDate.now();
        WalletDto walletDto=new WalletDto(125567,"Maven",1200.0,"Maven@gmail.com","Maven123",da,1002);
        assertThrows(WalletException.class,()->this.walletService.updateWallet("Maven@gmail.com","Maven123",walletDto));   // i enter wrong password here
    }
    @Test
    public void addFundsToWalletById() throws WalletException {
        walletService.addFundsToWalletById(125567,300.0);
        assertEquals(1500.0, walletService.getWalletById(125567,"james@gmail.com","James123").getBalance());
    }

    @Test
    public void addFundsToWalletByIdThrows(){
        assertThrows(WalletException.class,()->this.walletService.addFundsToWalletById(125590,1200.0));
    }

    @Test
    public void withdrawFundsFromWalletById()throws WalletException{
        walletService.withdrawFundsFromWalletById(125567,500.0,1001);
        assertEquals(700.0,walletService.getWalletById(125567,"james@gmail.com","James123").getBalance());
    }
    @Test
    public void withdrawFundsFromWalletByIdThrowsInsufficientBalance(){
        assertThrows(WalletException.class,()->walletService.withdrawFundsFromWalletById(125567,1500.0,1001));
    }
    @Test
    public void withdrawFundsFromWalletPinError(){
        assertThrows(WalletException.class,()->walletService.withdrawFundsFromWalletById(125567,500.0,1221));
    }

    @Test
    public void withdrawFundsFromWalletIdError(){
        assertThrows(WalletException.class,()->walletService.withdrawFundsFromWalletById(125564,500.0,1001));
    }

    @Test
    public void fundTransfer()throws WalletException{
        LocalDate da = LocalDate.now();
        WalletDto walletDto=new WalletDto(125568,"Maven",1200.0,"Maven@gmail.com","Maven123",da,1002);
        walletService.registerWallet(walletDto);
        walletService.fundTransfer(125567,125568,1001,1000.0);
        assertEquals(200.0,walletService.getWalletById(125567,"james@gmail.com","James123").getBalance());
    }
    @Test
    public void fundTransferSameAcc(){
        assertThrows(WalletException.class,()->walletService.fundTransfer(125567,125567,1001,1000.0));
    }
    @Test
    public void fundTransferwrongpin(){
        assertThrows(WalletException.class,()->walletService.fundTransfer(125567,125568,1061,1000.0));
    }
    @Test
    public void fundTransferbalance(){
        assertThrows(WalletException.class,()->walletService.fundTransfer(125567,125568,1001,10000.0));
    }

}
