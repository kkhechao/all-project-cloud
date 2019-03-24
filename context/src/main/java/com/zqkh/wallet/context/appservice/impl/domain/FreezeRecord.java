package com.zqkh.wallet.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.EntityObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author wenjie
 * @date 2017/11/23 0023 17:53
 * 冻结记录
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FreezeRecord extends EntityObject {

    public enum Type{
        FREEZE,
        UNFREEZE
    }


    /**
     * 操作类型
     */
    private Type type;

    /**
     * 操作金额
     */
    private BigDecimal money;

    /**
     * 操作人
     */
    private String user;

    /**
     * 备注
     */
    private String comment;

    /**
     * 操作时间
     */
    private LocalDateTime createTime;

    public void init(Type type, BigDecimal money, String user, String comment) {
        this.type = type;
        this.money = money;
        this.user = user;
        this.comment = comment;
        this.createTime = LocalDateTime.now();
    }
}
