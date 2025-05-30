package com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request;

import lombok.Getter;

@Getter
public class SignInCommand {
    private String loginId;
    private String password;
}
