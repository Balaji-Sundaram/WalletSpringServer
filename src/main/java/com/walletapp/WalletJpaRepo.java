package com.walletapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletJpaRepo extends JpaRepository< WalletDto,Integer> {

    List<WalletDto> findByName(String name);

    List<WalletDto> findWalletDtoById(Integer id);

    List<WalletDto>findByBalanceBetweenOrderByBalanceDesc(Double minBalance,Double maxBalance);

  WalletDto findByGmail(String eMail);

}
