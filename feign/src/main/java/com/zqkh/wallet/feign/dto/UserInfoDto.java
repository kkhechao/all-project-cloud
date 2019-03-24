package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hty
 * @create 2018-04-03 10:25
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private String userName;

    private String IDcardNumber;

    private String phoneNumber;

}
