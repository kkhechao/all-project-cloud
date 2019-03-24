package com.zqkh.wallet.context.event;

import com.jovezhao.nest.ddd.event.EventHandler;
import com.zqkh.shop.event.dto.CreateWalletAccountEventDto;
import com.zqkh.user.event.dto.UserFirstLoginEventDto;
import com.zqkh.wallet.context.appservice.inter.AccountService;

/**
 * @author wenjie
 * @date 2017/12/29 0029 11:42
 */
public class CreateShopWalletAccountEventHandler implements EventHandler<CreateWalletAccountEventDto> {

    private AccountService accountService;

    public CreateShopWalletAccountEventHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String getEventName() {
        return CreateWalletAccountEventDto.EVENT_NAME;
    }

    @Override
    public Class<CreateWalletAccountEventDto> getTClass() {
        return CreateWalletAccountEventDto.class;
    }

    @Override
    public void handle(CreateWalletAccountEventDto createWalletAccountEventDto) throws Exception {
        accountService.createWalletAccount(createWalletAccountEventDto.getAccountId());
    }
}
