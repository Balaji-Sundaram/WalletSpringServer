package com.walletapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService{
    @Autowired
    private WalletJpaRepo walletJpaRepo;    //jpa based service
    @Override
    public WalletDto registerWallet(WalletDto wallet) throws WalletException {
        int count =0;
        Integer n = wallet.getFundTransferPin();
        while(n != 0)
        {
            n = n / 10;
            count = count + 1;
        }
        if(count !=4 ){
            throw new WalletException("Enter 4 Digits only");
        }
            return this.walletJpaRepo.save(wallet);   //save the input data
    }

    @Override
    public WalletDto getWalletById(Integer walletId,String email,String password) throws WalletException {
        Optional<WalletDto> walletDto = this.walletJpaRepo.findById(walletId);    //optional is an object container
        if(walletDto.isEmpty())
            throw new WalletException("Wallet Not Found");
        else {
            String eMail = walletDto.get().geteMail();//walletRepository.getWalletById(walletId).geteMail();
            String pass = walletDto.get().getPassword();//walletRepository.getWalletById(walletId).getPassword();
            if (eMail.equals(email) && pass.equals(password)) {
                return walletDto.get();//walletRepository.getWalletById(walletId);
            }
            throw new WalletException("You Entered Wrong Credential Check Your Id, Email, Password ");
        }
    }
    @Override
    public WalletDto updateWallet( String email,String password,WalletDto wallet) throws WalletException {
       Optional<WalletDto> walletDto = this.walletJpaRepo.findById(wallet.getId());
        if(walletDto.isEmpty())
            throw new WalletException("Wallet Not Found");
        else {
            String eMail =walletDto.get().geteMail();// walletRepository.getWalletById(wallet.getId()).geteMail();
            String pass = walletDto.get().getPassword();// walletRepository.getWalletById(wallet.getId()).getPassword();
            if (eMail.equals(email) && pass.equals(password)) {
                return  walletJpaRepo.save(wallet); //walletRepository.updateWallet(wallet);
            }
            throw new WalletException("You Entered Wrong Credential Check Your Id, Email, Password ");
        }
        }



    @Override
    public WalletDto deleteWalletById(Integer walletId,String email,String password) throws WalletException {
        Optional<WalletDto> walletDto = walletJpaRepo.findById(walletId);
        if(walletDto.isEmpty())
            throw new WalletException("Wallet Not Found");
        else {
            String eMail = walletDto.get().geteMail();//walletRepository.getWalletById(walletId).geteMail();
            String pass =  walletDto.get().getPassword();//walletRepository.getWalletById(walletId).getPassword();
            if ( eMail.equals(email) && pass.equals(password)) {
                WalletDto foundWallet = walletDto.get();
                this.walletJpaRepo.delete(foundWallet);
                return foundWallet;
            }
            throw new WalletException("You Entered Wrong Credential Check Your Id, Email, Password ");
        }
    }

    @Override
    public Double addFundsToWalletById(Integer walletId, Double amount) throws WalletException {
        Optional<WalletDto> walletDto = this.walletJpaRepo.findById(walletId);
        if(walletDto.isEmpty())
            throw new WalletException("Wallet Not Found");
        else {
            Double temp =walletJpaRepo.findById(walletId).get().getBalance();
             WalletDto walletDto1 = this.walletJpaRepo.getReferenceById(walletId);
             walletDto1.setBalance(temp+amount);
              this.walletJpaRepo.save(walletDto1);

          //  this.walletJpaRepo.findById(walletId).get().setBalance(temp+amount);//     .setBalance( walletRepository.getWalletById(walletId).getBalance()+amount);
            return   this.walletJpaRepo.findById(walletId).get().getBalance();//walletRepository.getWalletById(walletId).getBalance();
        }
    }

    @Override    //mistaken yesterday
    public Double withdrawFundsFromWalletById(Integer walletById, Double amount,Integer pin) throws WalletException {
        Optional<WalletDto> walletDto = this.walletJpaRepo.findById(walletById);
        if(walletDto.isEmpty())
            throw new WalletException("Wallet Not Found");
          else {
                 if( pin.equals(this.walletJpaRepo.findById(walletById).get().getFundTransferPin())) {                //walletRepository.getWalletById(walletById).getFundTransferPin()
                     Double balance =   this.walletJpaRepo.findById(walletById).get().getBalance();                    //walletRepository.getWalletById(walletById).getBalance();
                     String toBalance= balance.toString();
                     if(balance<amount)
                         throw new WalletException("Insufficient Balance and your Balance: "+toBalance );
                     WalletDto walletDto1 = this.walletJpaRepo.getReferenceById(walletById);
                      walletDto1.setBalance(balance-amount);
                      this.walletJpaRepo.save(walletDto1);

                     return this.walletJpaRepo.findById(walletById).get().getBalance();
            }
                throw new WalletException("Wrong Pin or Wrong Wallet ID");
        }
    }

    @Override
    public Boolean fundTransfer(Integer fromWalletId, Integer toWalletId, Integer pin ,Double amount) throws WalletException {
        Optional<WalletDto> walletDto = this.walletJpaRepo.findById(fromWalletId);
        Optional<WalletDto> walletDto1 = this.walletJpaRepo.findById(toWalletId);
        WalletDto fromWallet =  this.walletJpaRepo.getReferenceById(fromWalletId);
        WalletDto toWallet = this.walletJpaRepo.getReferenceById(toWalletId);
        if(walletDto.isEmpty()  || walletDto1.isEmpty()  || fromWalletId.equals(toWalletId) )
            throw new WalletException("No Debtor or Creditor Found");
        else {
            if( pin.equals(this.walletJpaRepo.findById(fromWalletId).get().getFundTransferPin())) {
                Double balance = fromWallet.getBalance();
                Double tobalance = toWallet.getBalance();
                String toBalance= balance.toString();
                if(this.walletJpaRepo.findById(fromWalletId).get().getBalance()<amount)
                    throw new WalletException("Insufficient Balance and your Balance: "+toBalance);

                fromWallet.setBalance(balance-amount);
                toWallet.setBalance(tobalance+amount);
                this.walletJpaRepo.save(fromWallet);
                this.walletJpaRepo.save(toWallet);
                return true;
            }
            throw new WalletException("Wrong Pin or Wrong Wallet ID");
        }
    }

    @Override
    public List<WalletDto> findByName(String name)  {    //recent one
                    return walletJpaRepo.findByName(name);
    }

    @Override
    public List<WalletDto> findByBalanceBetweenOrderByBalanceBalanceDesc(Double minBalance, Double maxBalance) {
        return walletJpaRepo.findByBalanceBetweenOrderByBalanceDesc(minBalance,maxBalance);
    }

    @Override
    public List<WalletDto> getAllWallets() {
         return walletJpaRepo.findAll();
    }

    @Override
    public List<WalletDto> findbyId(Integer id) {
       return this.walletJpaRepo.findWalletDtoById(id);
    }
}
