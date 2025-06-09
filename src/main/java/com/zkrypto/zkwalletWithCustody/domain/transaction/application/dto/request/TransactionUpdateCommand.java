package com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionUpdateCommand {
    private Long transactionId;
}
