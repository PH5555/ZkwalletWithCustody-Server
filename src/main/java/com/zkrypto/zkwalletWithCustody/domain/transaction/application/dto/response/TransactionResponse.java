package com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.response;

import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TransactionResponse {
    private int fromPrivateAmount;
    private int fromPublicAmount;
    private int unSpentNote;
    private int totalInput;
    private int toPublicAmount;
    private int toPrivateAmount;
    private int totalOutput;
    private int remainingAmount;
    private String receiverAddress;
    private String receiverName;
    private String senderAddress;
    private String senderName;
    private LocalDateTime createdAt;
    private LocalDateTime signedAt;
    private Status status;

    public TransactionResponse(int fromPrivateAmount, int fromPublicAmount, int unSpentNote, int totalInput, int toPublicAmount, int toPrivateAmount, int totalOutput, int remainingAmount, String receiverAddress, String receiverName, String senderAddress, String senderName, LocalDateTime createdAt, LocalDateTime signedAt, Status status) {
        this.fromPrivateAmount = fromPrivateAmount;
        this.fromPublicAmount = fromPublicAmount;
        this.unSpentNote = unSpentNote;
        this.totalInput = totalInput;
        this.toPublicAmount = toPublicAmount;
        this.toPrivateAmount = toPrivateAmount;
        this.totalOutput = totalOutput;
        this.remainingAmount = remainingAmount;
        this.receiverAddress = receiverAddress;
        this.receiverName = receiverName;
        this.senderAddress = senderAddress;
        this.senderName = senderName;
        this.createdAt = createdAt;
        this.signedAt = signedAt;
        this.status = status;
    }

    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(transaction.getFromPrivateAmount(), transaction.getFromPublicAmount(), transaction.getUnSpentNote(),
                transaction.getTotalInput(), transaction.getToPublicAmount(), transaction.getToPrivateAmount(),
                transaction.getTotalOutput(), transaction.getRemainingAmount(), transaction.getReceiver().getAddress(),
                transaction.getReceiver().getName(), transaction.getSender().getAddress(), transaction.getSender().getName(),
                transaction.getCreatedAt(), transaction.getSignedAt(), transaction.getStatus());
    }
}
