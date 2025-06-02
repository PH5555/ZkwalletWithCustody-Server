package com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WalletCreationCommand {
    private String corporationId;
}
