package com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionCreationCommand {
    private int fromPrivateAmount;
    private int fromPublicAmount;
    private int unSpentNote;
    private int totalInput;
    private int toPublicAmount;
    private int toPrivateAmount;
    private int totalOutput;
    private int remainingAmount;
    private String receiverAddress;
    private String password;
}
