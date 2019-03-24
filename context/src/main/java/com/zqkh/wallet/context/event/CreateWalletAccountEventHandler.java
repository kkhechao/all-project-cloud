package com.zqkh.wallet.context.event;

import com.jovezhao.nest.ddd.event.EventHandler;
import com.zqkh.user.event.dto.UserFirstLoginEventDto;
import com.zqkh.wallet.context.appservice.inter.AccountService;

/**
 * @author wenjie
 * @date 2017/12/29 0029 11:42
 */
public class CreateWalletAccountEventHandler implements EventHandler<UserFirstLoginEventDto> {

    private AccountService accountService;

    public CreateWalletAccountEventHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String getEventName() {
        return UserFirstLoginEventDto.EVENT_NAME;
    }

    @Override
    public Class<UserFirstLoginEventDto> getTClass() {
        return UserFirstLoginEventDto.class;
    }

    @Override
    public void handle(UserFirstLoginEventDto createWalletAccountEventDto) throws Exception {
        accountService.createWalletAccount(createWalletAccountEventDto.getAccountId());
    }
}
