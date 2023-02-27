package com.walletapp;

import java.util.List;

public interface WalletService {

    WalletDto registerWallet(WalletDto wallet)throws WalletException ;
    WalletDto getWalletById(Integer walletId,String email,String password) throws WalletException;
    WalletDto updateWallet(String email,String password,WalletDto wallet)throws WalletException;
    WalletDto deleteWalletById(Integer walletId,String email,String password)throws WalletException;


    Double addFundsToWalletById(Integer walletId,Double amount)throws WalletException;
    Double withdrawFundsFromWalletById(Integer walletById,Double amount,Integer pin) throws WalletException;
    Boolean fundTransfer(Integer fromWalletId,Integer toWalletId,Integer pin,Double amount)throws WalletException;

    public List<WalletDto> findByName(String name);

    public List<WalletDto>findByBalanceBetweenOrderByBalanceBalanceDesc(Double minBalance,Double maxBalance);
    List<WalletDto> getAllWallets();
}
