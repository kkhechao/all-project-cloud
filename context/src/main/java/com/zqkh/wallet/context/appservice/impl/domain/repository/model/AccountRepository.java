package com.zqkh.wallet.context.appservice.impl.domain.repository.model;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.EntityObjectUtils;
import com.jovezhao.nest.ddd.repository.IRepository;
import com.zqkh.wallet.context.appservice.impl.domain.Account;
import com.zqkh.wallet.context.appservice.impl.domain.BankCard;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.AccountDmoMapper;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.AccountDmo;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.BankCardDmo;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.ext.BankCardDmoDmlMapper;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository("Account_Repository")
public class AccountRepository implements IRepository<Account> {

    public static final String BANK_CARD_FIELD = "bankCardList";
    @Autowired
    private AccountDmoMapper accountDmoMapper;
    @Autowired
    private BankCardDmoDmlMapper bankCardDmoDmlMapper;

    @Autowired
    private DozerBeanMapper modelMapper;

    @Override
    public Account getEntityById(Identifier identifier, EntityLoader<Account> entityLoader) {
        AccountDmo accountDmo = accountDmoMapper.selectByPrimaryKey(identifier.toValue());
        if (accountDmo == null) {
            return null;
        }
        Account account = entityLoader.create(identifier);
        modelMapper.map(accountDmo, account);

        List<BankCardDmo> bankDmos = bankCardDmoDmlMapper.selectByAccountId(identifier.toValue());
        if (bankDmos != null) {
            EntityObjectUtils.setValue(Account.class, account, BANK_CARD_FIELD, bankDmos.stream().map(p -> modelMapper.map(p, BankCard.class)).collect(Collectors.toList()));
        }
        return account;
    }

    @Override
    public void save(Account account) {
        if (account == null) {
            return;
        }

        AccountDmo accountDmo = modelMapper.map(account, AccountDmo.class);

        if(accountDmoMapper.updateByPrimaryKey(accountDmo) == 0){
            accountDmoMapper.insert(accountDmo);
        }

        List<BankCard> bankCardList = account.getBankCardList();
        if (bankCardList != null) {
            //删除仅有的一张银行卡情况,size() == 0
            bankCardDmoDmlMapper.deleteByAccountId(account.getId().toValue());
            if(bankCardList.size() > 0){
                List<BankCardDmo> bankDmoList = bankCardList
                        .stream()
                        .map(p -> modelMapper.map(p, BankCardDmo.class))
                        .collect(Collectors.toList());
                bankCardDmoDmlMapper.batchInsert(account.getId().toValue(), bankDmoList);
            }
        }
    }

    @Override
    public void remove(Account account) {
        if(account == null){
            return;
        }
        accountDmoMapper.deleteByPrimaryKey(account.getId().toValue());
        bankCardDmoDmlMapper.deleteByAccountId(account.getId().toValue());
    }
}
