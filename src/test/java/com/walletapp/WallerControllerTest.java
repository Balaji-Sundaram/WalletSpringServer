package com.walletapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import javax.sound.midi.Patch;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// it use random port not a specific port
public class WallerControllerTest {
    LocalDate date = LocalDate.now();
    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private WalletService walletService;
    @Autowired
    private TestRestTemplate restTemplate;
    @BeforeEach
    public void init(){//if we use beforeEach this method will execute before each method
        this.restTemplate.postForObject("http://localhost:" + port + "/addWallet",     new WalletDto(125567,"Maven",1200.0,"Maven@gmail.com","Maven123",date,1001),WalletDto.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/addWallet",     new WalletDto(125568,"James",100.0,"James@gmail.com","James123",date,1002),WalletDto.class);
    }


    //--------------------------------------------------------------------------->Get Method Check
    @Test
    public void getWalletByIdTest() throws WalletException {
        WalletDto walletDto = this.restTemplate.getForObject("http://localhost:"+port+"/getWallet/E-Mail/Maven@gmail.com/Password/Maven123/ID/125567",WalletDto.class);
        assertEquals("Maven", walletDto.getName());
    }
    @Test
    public void getWalletByIdTestThrow()throws WalletException {   //Wrong ID exception
        String exception = this.restTemplate.getForObject("http://localhost:" + port + "/getWallet/E-Mail/Maven@gmail.com/Password/Maven123/ID/125590", String.class);
        assertEquals("Wallet Not Found", exception);
    }
    @Test
    public void getWalletByIdTestThrow1()throws WalletException {   //Wrong Password  and mail exception (same message will return for both password and mail wrong
        String exception = this.restTemplate.getForObject("http://localhost:" + port + "/getWallet/E-Mail/Maven@.com/Password/MavenQQQ/ID/125567", String.class);
        assertEquals("You Entered Wrong Credential Check Your Id, Email, Password ", exception);
    }
//----------------------------------------------------------------------------->End of Get Testing

//----------------------------------------------------------------------------->Update(Post Method) Checking
    @Test
    public void updateWalletTest()throws WalletException{
      //     "http://localhost:"+port+"/updateWallet/E-Mail/Maven@gmail.com/Password/Maven123"
                WalletDto walletDto =  new WalletDto(125567,"Tyrion",1200.0,"Tyrion@gmail.com","Tyrion12",date,1003);
                this.restTemplate.put("http://localhost:"+port+"/updateWallet/E-Mail/Maven@gmail.com/Password/Maven123",walletDto,WalletDto.class);
                WalletDto walletDto1 = this.restTemplate.getForObject("http://localhost:"+port+"/getWallet/E-Mail/Tyrion@gmail.com/Password/Tyrion12/ID/125567",WalletDto.class);
        assertEquals("Tyrion", walletDto1.getName());
    }



//--------------------------------------------------------------------------->End of Update(Post Method) Checking

//---------------------------------------------------------------------------> Delete Wallet

    @Test
    public void deleteWalletByIdTest()throws WalletException{      //this method test deletion and exception
       this.restTemplate.delete("http://localhost:"+port+"/deleteWallet/E-Mail/Maven@gmail.com/Password/Maven123/ID/125567");
        String exception = this.restTemplate.getForObject("http://localhost:" + port + "/getWallet/E-Mail/Maven@gmail.com/Password/Maven123/ID/125567", String.class);
        assertEquals("Wallet Not Found", exception);
    }

//---------------------------------------------------------------------------> End of Delete Wallet


//---------------------------------------------------------------------------> addFundsToWalletById  (Patch Mapping)
@Test
    public void addFundsToWalletByIdTest() throws WalletException{   //checking the Add funds is working or not
//        RestTemplate restTemplate = new RestTemplate();
//    HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
  Double balance =   this.restTemplate.patchForObject( "http://localhost:"+port+"/addFundstoWallet/ID/125567/Amount/1500.65 ", null,Double.class);
     assertEquals(2700.65, balance);
}

    @Test
    public void addFundsToWalletByIdTest1() throws WalletException{   // exception throw while wrong ID passes
       String exception = this.restTemplate.patchForObject( "http://localhost:"+port+"/addFundstoWallet/ID/125590/Amount/1500.65", null,String.class);
   assertEquals("Wallet Not Found",exception);
    }
//---------------------------------------------------------------------------> End of addFundsToWalletById  (post Mapping)


//---------------------------------------------------------------------------> withdrawFundsToWalletById  (post Mapping)
    @Test
    public void withdrawFundsFromWalletByIdTest() throws WalletException{  //check the with withdraw only
          Double remainBalance = this.restTemplate.patchForObject("http://localhost:"+port+"/withdrawFunds/ID/125567/Pin/1001/Amount/1000",null,Double.class);
          assertEquals(200.0,remainBalance);
    }
    @Test
    public void withdrawFundsFromWalletByIdTestThrow() throws WalletException{  //wrong pin
       String exception = this.restTemplate.patchForObject("http://localhost:"+port+"/withdrawFunds/ID/125567/Pin/1000/Amount/1000",null,String.class);
        assertEquals("Wrong Pin or Wrong Wallet ID",exception);
    }
    @Test
    public void withdrawFundsFromWalletByIdTestThrow1() {  //wrong ID
        String exception = this.restTemplate.patchForObject("http://localhost:"+port+"/withdrawFunds/ID/125590/Pin/1001/Amount/1000",null ,String.class);
        assertEquals("Wallet Not Found",exception);
    }
    @Test
    public void withdrawFundsFromWalletByIdTestThrow2() throws WalletException{  //Higher Amount enter as withdraw
        String exception =  this.restTemplate.patchForObject("http://localhost:"+port+"/withdrawFunds/ID/125567/Pin/1001/Amount/10000.0",null,String.class);
        assertEquals("Insufficient Balance and your Balance: 1200.0" ,exception);
    }


//--------------------------------------------------------------------------->End of withdrawFundsToWalletById  (post Mapping)


//---------------------------------------------------------------------------> Fund Transfer  (post Mapping)
@Test
    public void fundTransferTest()throws WalletException{  // normal Transaction
        Boolean statusofTransac = this.restTemplate.patchForObject("http://localhost:"+port+"/fundTransfer/ID/125567/CreditorID/125568/Pin/1001/Amount/610.0",null,Boolean.class);
        assertEquals(true,statusofTransac);
}
    @Test
    public void fundTransferTestThrows()throws WalletException{  //  exception on wrong wallet
     String statusofTransac = this.restTemplate.patchForObject("http://localhost:"+port+"/fundTransfer/ID/125590/CreditorID/125568/Pin/1001/Amount/610.0",null,String.class);
        assertEquals( "No Debtor or Creditor Found",statusofTransac);
    }
    @Test
    public void fundTransferTestThrows2()throws WalletException{  //  exception on same wallet
        String statusofTransac = this.restTemplate.patchForObject("http://localhost:"+port+"/fundTransfer/ID/125568/CreditorID/125568/Pin/1001/Amount/610.0",null,String.class);
        assertEquals( "No Debtor or Creditor Found",statusofTransac);
    }
    @Test
    public void fundTransferTestThrows3()throws WalletException{  //  exception on insufficient fund
        String statusofTransac = this.restTemplate.patchForObject("http://localhost:"+port+"/fundTransfer/ID/125567/CreditorID/125568/Pin/1001/Amount/10000.0",null,String.class);
        assertEquals( "Insufficient Balance and your Balance: 1200.0",statusofTransac);
    }
    @Test
    public void fundTransferTestThrow4s()throws WalletException{  //  exception on wrong pin
        String statusofTransac = this.restTemplate.patchForObject("http://localhost:"+port+"/fundTransfer/ID/125567/CreditorID/125568/Pin/1097/Amount/610.0",null,String.class);
        assertEquals( "Wrong Pin or Wrong Wallet ID",statusofTransac);
    }

//---------------------------------------------------------------------------> End of Fund Transfer  (post Mapping)

//---------------------------------------------------------------------------> Get All Wallet (get Mapping)
//    @Test
//    public void getAllWallets(){  //two data are given at before each statement
//        List<WalletDto> walletDtos = this.restTemplate.getForObject("http://localhost:"+port+"/getAllWallets",List.class);
//        assertEquals(3,walletDtos.size());
//    }
//---------------------------------------------------------------------------> End of Get All Wallet (get Mapping)
}
