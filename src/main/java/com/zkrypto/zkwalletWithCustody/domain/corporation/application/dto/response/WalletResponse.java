package com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.constant.UPK;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WalletResponse {
    private String address;
    private String ena;
    private String pkOwn;
    private String pkEncX;
    private String pkEncY;
    private String sk;

    public static WalletResponse from(String address, UPK upk, String sk) {
        return new WalletResponse(address, upk.getEna().toString(), upk.getPkOwn().toString(), upk.getPkEnc().getX().toString(), upk.getPkEnc().getY().toString(), sk);
    }
}
