package com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request;

import lombok.Getter;

@Getter
public class SignUpCommand {
    private String name;
    private String corporationId;
    private String password;
    private String loginId;
    private String position;
}
