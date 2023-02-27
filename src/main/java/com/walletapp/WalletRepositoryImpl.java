package com.walletapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WalletRepositoryImpl implements WalletRepository{
Map<Integer,WalletDto> walletDtoMap = new HashMap<>();
    @Override
    public WalletDto createWallet(WalletDto newWallet) {
            return walletDtoMap.put(newWallet.getId(),newWallet);
    }
    @Override
    public WalletDto getWalletById(Integer walletId) {
        return walletDtoMap.get(walletId);
    }
    @Override
    public WalletDto updateWallet(WalletDto wallet) {
        return walletDtoMap.put(wallet.getId(),wallet);
    }

    @Override
    public WalletDto deleteWalletById(Integer walletId) {
       return walletDtoMap.remove(walletId);

    }

    @Override
    public List<WalletDto> getAllWallets() {
        ArrayList<Integer> keyList = new ArrayList<>(walletDtoMap.keySet());
        ArrayList<WalletDto> objectList = new ArrayList<>(walletDtoMap.values());
        return objectList;
    }
}
