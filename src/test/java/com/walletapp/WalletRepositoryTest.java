package com.walletapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class WalletRepositoryTest {
           @Autowired
    private WalletJpaRepo walletJpaRepo;
    @BeforeEach
    public void init()   {
        LocalDate da = LocalDate.now();
        walletJpaRepo.save(new WalletDto(125567,"james",1200.0,"james@gmail.com","James123",da,1001));
    }

    @Test
    public void getWallet() {
        assertEquals("james", walletJpaRepo.getReferenceById(125567).getName());
    }
    @Test
    public void updateWallet() {
        LocalDate da = LocalDate.now();
        WalletDto walletDto=new WalletDto(125567,"Maven",1200.0,"Maven@gmail.com","Maven123",da,1002);
        walletJpaRepo.save(  walletDto);  //first update
        assertEquals("Maven", walletJpaRepo.getReferenceById(125567).getName());  //check with new data
    }



}
