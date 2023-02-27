package com.walletapp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;


//------------------------>  Please Note:  The Normal Repository test cases are deleted and Re-Written for Jpa Repository....

@SpringBootTest
public class MockRepositoryTest {
    @Autowired
    private WalletService walletService;
    @MockBean
    private WalletJpaRepo walletJpaRepo;
    LocalDate date = LocalDate.now();

    @BeforeEach
    public void init() throws WalletException {
        WalletDto walletDto =new WalletDto(125567,"James",1200.0,"James@gmail.com","James123",date,1001);
        when(this.walletJpaRepo.findById(125567)).thenReturn(Optional.of(walletDto));
    }
    @Test
    public void getWallet() throws WalletException {
        assertEquals("James",  this.walletService.getWalletById(125567,"James@gmail.com","James123").getName());
    }

    @Test
    public void getWalletThrows()throws WalletException{   //exception on get wallet
        assertThrows(WalletException.class,()->this.walletService.getWalletById(125590,"Ja@gmail.com","James123"));
    }

  @Test
public void updateWallet()throws WalletException{   //updating the existing wallet
    WalletDto walletDto= new WalletDto(125567,"Maven",1200.0,"Maven@gmail.com","James123",date,1001);
        when(this.walletJpaRepo.save(walletDto))
                .thenReturn(new WalletDto(125567,"Maven",1200.0,"Maven@gmail.com","James123",date,1001));
 assertEquals("Maven",   this.walletService.updateWallet("James@gmail.com","James123",walletDto).getName());
  }
   @Test
     public void updateWalletThrows()throws WalletException{   //exception update wallet if the password or main is wrong
         WalletDto walletDto=new WalletDto(125567,"Maven",1200.0,"Maven@gmail.com","James123",date,1001);
        when(this.walletJpaRepo.save(walletDto))
                .thenReturn(walletDto);
      assertThrows(WalletException.class,()->this.walletService.updateWallet("James@gmail.com","jamie",walletDto));
     }

    @Test
     public void addFundsToWalletById()throws WalletException{
        WalletDto walletDto = this.walletService.getWalletById(125567,"James@gmail.com","James123");
        when(this.walletJpaRepo.save(walletDto)).thenReturn(walletDto);
        when(this.walletJpaRepo.getReferenceById(125567)).thenReturn(walletDto);    //method use in service so have to declare here
        assertEquals(2700.0, this.walletService.addFundsToWalletById(125567,1500.0));
    }
    @Test
    public void addFundsToWalletByIdThrows()throws WalletException{   // method must throw exception
        WalletDto walletDto = this.walletService.getWalletById(125567,"James@gmail.com","James123");
         when(this.walletJpaRepo.save(walletDto)).thenReturn(walletDto);
         when(this.walletJpaRepo.getReferenceById(125567)).thenReturn(walletDto);
       assertThrows(WalletException.class,()->walletService.addFundsToWalletById(125590,1500.0));

    }

@Test
public void withdrawFundsFromWalletById()throws WalletException{   // Normal withdraw method
    WalletDto walletDto = this.walletService.getWalletById(125567,"James@gmail.com","James123");
    when(this.walletJpaRepo.save(walletDto)).thenReturn(walletDto);
    when(this.walletJpaRepo.getReferenceById(125567)).thenReturn(walletDto);
    assertEquals(200.0,walletService.withdrawFundsFromWalletById(125567,1000.0,1001));
}
    @Test
    public void withdrawFundsFromWalletByIdThrow()throws WalletException{   // method must throw exception
        WalletDto walletDto = this.walletService.getWalletById(125567,"James@gmail.com","James123");
        when(this.walletJpaRepo.save(walletDto)).thenReturn(walletDto);
        when(this.walletJpaRepo.getReferenceById(125567)).thenReturn(walletDto);
        assertThrows(WalletException.class,()->walletService.withdrawFundsFromWalletById(125567,1000000.0,1001));
    }
   @Test
    public void fundTransfer()throws WalletException{   //normal method to transfer fund
         WalletDto walletDto1 = new WalletDto(125568,"Maven",1200.0,"Maven@gmail.com","Maven123",date,1002);
       WalletDto walletDto = this.walletService.getWalletById(125567,"James@gmail.com","James123");
       when(this.walletJpaRepo.findById(125568)).thenReturn(Optional.of(walletDto1));

       when(this.walletJpaRepo.save(walletDto)).thenReturn(walletDto);
       when(this.walletJpaRepo.getReferenceById(125567)).thenReturn(walletDto);

       when(this.walletJpaRepo.save(walletDto1)).thenReturn(walletDto1);
       when(this.walletJpaRepo.getReferenceById(125568)).thenReturn(walletDto1);

  assertEquals(true,walletService.fundTransfer(125567,125568,1001,1000.0));
   }
    @Test
    public void fundTransferThrows()throws WalletException{   //Exception happen when the pin goes wrong
        WalletDto walletDto1 = new WalletDto(125568,"Maven",1200.0,"Maven@gmail.com","Maven123",date,1002);
        WalletDto walletDto = this.walletService.getWalletById(125567,"James@gmail.com","James123");
        when(this.walletJpaRepo.findById(125568)).thenReturn(Optional.of(walletDto1));

        when(this.walletJpaRepo.save(walletDto)).thenReturn(walletDto);
        when(this.walletJpaRepo.getReferenceById(125567)).thenReturn(walletDto);

        when(this.walletJpaRepo.save(walletDto1)).thenReturn(walletDto1);
        when(this.walletJpaRepo.getReferenceById(125568)).thenReturn(walletDto1);

        assertThrows(WalletException.class,()->walletService.fundTransfer(125567,125568,112123,1000.0));
    }
    @Test
    public void fundTransferThrows1()throws WalletException{   //Exception happen when the Out of Funds
        WalletDto walletDto1 = new WalletDto(125568,"Maven",1200.0,"Maven@gmail.com","Maven123",date,1002);
        WalletDto walletDto = this.walletService.getWalletById(125567,"James@gmail.com","James123");
        when(this.walletJpaRepo.findById(125568)).thenReturn(Optional.of(walletDto1));

        when(this.walletJpaRepo.save(walletDto)).thenReturn(walletDto);
        when(this.walletJpaRepo.getReferenceById(125567)).thenReturn(walletDto);

        when(this.walletJpaRepo.save(walletDto1)).thenReturn(walletDto1);
        when(this.walletJpaRepo.getReferenceById(125568)).thenReturn(walletDto1);

        assertThrows(WalletException.class,()->walletService.fundTransfer(125567,125568,1001,10000000.0));
    }
    @Test
    public void fundTransferThrows2()throws WalletException{   //Exception happen when the Wrong ID
        WalletDto walletDto1 = new WalletDto(125568,"Maven",1200.0,"Maven@gmail.com","Maven123",date,1002);
        WalletDto walletDto = this.walletService.getWalletById(125567,"James@gmail.com","James123");
        when(this.walletJpaRepo.findById(125568)).thenReturn(Optional.of(walletDto1));

        when(this.walletJpaRepo.save(walletDto)).thenReturn(walletDto);
        when(this.walletJpaRepo.getReferenceById(125567)).thenReturn(walletDto);

        when(this.walletJpaRepo.save(walletDto1)).thenReturn(walletDto1);
        when(this.walletJpaRepo.getReferenceById(125568)).thenReturn(walletDto1);

        assertThrows(WalletException.class,()->walletService.fundTransfer(123456,123456,1001,100.0));
    }
   @Test
    public void findByName() throws WalletException {
       WalletDto walletDto = this.walletService.getWalletById(125567,"James@gmail.com","James123");
       List<WalletDto> walletDtoList = new ArrayList<>();
       walletDtoList.add(walletDto);
       when(this.walletJpaRepo.findByName("James")).thenReturn(walletDtoList);
       assertEquals("James",this.walletService.findByName("James").get(0).getName());
   }


}
