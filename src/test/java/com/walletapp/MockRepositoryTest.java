package com.walletapp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;

//    WalletDto createWallet(WalletDto newWallet);
//            WalletDto getWalletById(Integer  walletId);
//            WalletDto updateWallet(WalletDto wallet);
//            WalletDto deleteWalletById(Integer walletId);
//            Double addFundsToWalletById(Integer walletId,Double amount) ;
//            Double withdrawFundsFromWalletById(Integer walletById,Double amount) ;
//            Boolean fundTransfer(Integer fromWalletId,Integer toWalletId,Double amount) ;
//public  List<WalletDto> getAllWallets();

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class MockRepositoryTest {

    @Autowired
    private WalletService walletService;
    @MockBean
    private WalletRepository walletRepository;
    LocalDate date = LocalDate.now();
//    @BeforeEach
//    public void init() throws WalletException {
//        this.walletService.registerWallet(new WalletDto(125567,"james",1200.0,"james@gmail.com","James123",date,1001));
//    }
    @Test
    public void getWallet()throws WalletException{
        // at first, we have to train the data then will be executed, so we don't bind with the original database
        // in here we create the proxy database
        given(this.walletRepository.getWalletById(125567))
                .willReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
        assertEquals("James",this.walletService.getWalletById(125567,"James@gmail.com","James123").getName());
    }

    @Test
    public void getWalletThrows()throws WalletException{   //exception on get wallet
        given(this.walletRepository.getWalletById(125567))
                .willReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
        assertThrows(WalletException.class,()->this.walletService.getWalletById(125590,"Ja@gmail.com","James123"));
    }
    //WalletDto walletDto1 =new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001);
    // this.walletService.registerWallet(new WalletDto(125567,"james",1200.0,"james@gmail.com","James123",date,1001));
    @Test
    public void updateWallet()throws WalletException{   //updating the existing wallet
    WalletDto walletDto=new WalletDto(125567,"Maven",1200.0,"Maven@gmail.com","James123",date,1001);
        when(this.walletRepository.getWalletById(125567))
                .thenReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
        when(this.walletRepository.updateWallet(walletDto))
                .thenReturn(walletDto);
  assertEquals("Maven",   this.walletService.updateWallet("James@gmail.com","James123",walletDto).getName());
    }
    @Test
    public void updateWalletThrows()throws WalletException{   //exception update wallet if the password or main is wrong
        WalletDto walletDto=new WalletDto(125567,"Maven",1200.0,"Maven@gmail.com","James123",date,1001);
        when(this.walletRepository.getWalletById(125567))
                .thenReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
        when(this.walletRepository.updateWallet(walletDto))
                .thenReturn(walletDto);
       assertThrows(WalletException.class,()->this.walletService.updateWallet("James@gmail.com","jamie",walletDto));
    }
    //
    @Test
    public void addFundsToWalletById()throws WalletException{
        given(this.walletRepository.getWalletById(125567))
                .willReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
       Double newBalance =  this.walletService.addFundsToWalletById(125567,1500.0);
        assertEquals(2700.0, newBalance  );

    }
    @Test
    public void addFundsToWalletByIdThrows()throws WalletException{   // method must throw exception
        when(this.walletRepository.getWalletById(125567))
                .thenReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
       assertThrows(WalletException.class,()->walletService.addFundsToWalletById(125590,1500.0));

    }
//
@Test
public void withdrawFundsFromWalletById()throws WalletException{   // Normal withdraw method
    when(this.walletRepository.getWalletById(125567))
            .thenReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
assertEquals(200.0,walletService.withdrawFundsFromWalletById(125567,1000.0,1001));
}
    @Test
    public void withdrawFundsFromWalletByIdThrow()throws WalletException{   // method must throw exception
        when(this.walletRepository.getWalletById(125567))
                .thenReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
         assertThrows(WalletException.class,()->walletService.withdrawFundsFromWalletById(125567,1000000.0,1001));
    }
   @Test
    public void fundTransfer()throws WalletException{   //normal method to transfer fund
       when(this.walletRepository.getWalletById(125567))
               .thenReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
    when(this.walletRepository.getWalletById(125568))
            .thenReturn(new WalletDto(125568,"Maven",1200.0,"Maven@gmail.com","Maven123",date,1002));
  assertEquals(true,walletService.fundTransfer(125567,125568,1001,1000.0));
   }
    @Test
    public void fundTransferThrows()throws WalletException{   //Exception happen when the pin goes wrong
        when(this.walletRepository.getWalletById(125567))
                .thenReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
        when(this.walletRepository.getWalletById(125568))
                .thenReturn(new WalletDto(125568,"Maven",1200.0,"Maven@gmail.com","Maven123",date,1002));
        assertThrows(WalletException.class,()->walletService.fundTransfer(125567,125568,112123,1000.0));
    }
    @Test
    public void fundTransferThrows1()throws WalletException{   //Exception happen when the Out of Funds
        when(this.walletRepository.getWalletById(125567))
                .thenReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
        when(this.walletRepository.getWalletById(125568))
                .thenReturn(new WalletDto(125568,"Maven",1200.0,"Maven@gmail.com","Maven123",date,1002));
        assertThrows(WalletException.class,()->walletService.fundTransfer(125567,125568,1001,10000000.0));
    }
    @Test
    public void fundTransferThrows2()throws WalletException{   //Exception happen when the Wrong ID
        when(this.walletRepository.getWalletById(125567))
                .thenReturn(new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001));
        when(this.walletRepository.getWalletById(125568))
                .thenReturn(new WalletDto(125568,"Maven",1200.0,"Maven@gmail.com","Maven123",date,1002));
        assertThrows(WalletException.class,()->walletService.fundTransfer(123456,123456,1001,100.0));
    }



}
