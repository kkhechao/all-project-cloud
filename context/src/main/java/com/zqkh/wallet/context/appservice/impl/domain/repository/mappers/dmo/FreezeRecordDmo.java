package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FreezeRecordDmo {
    private String id;

    private String type;

    private Long money;

    private String userId;

    private String comment;

    private Date createTime;
}