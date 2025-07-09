package com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.response;

import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TransactionResponse {
    private Long transactionId;
    private int fromPrivateAmount;
    private int fromPublicAmount;
    private UnSpentNote fromUnSpentNote;
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

    public TransactionResponse(Long transactionId, int fromPrivateAmount, int fromPublicAmount, Note fromUnSpentNote, int totalInput, int toPublicAmount, int toPrivateAmount, int totalOutput, int remainingAmount, String receiverAddress, String receiverName, String senderAddress, String senderName, LocalDateTime createdAt, LocalDateTime signedAt, Status status) {
        this.transactionId = transactionId;
        this.fromPrivateAmount = fromPrivateAmount;
        this.fromPublicAmount = fromPublicAmount;
        this.fromUnSpentNote = UnSpentNote.from(fromUnSpentNote);
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
        return new TransactionResponse(transaction.getId(), transaction.getFromPrivateAmount(), transaction.getFromPublicAmount(), transaction.getFromUnSpentNote(),
                transaction.getTotalInput(), transaction.getToPublicAmount(), transaction.getToPrivateAmount(),
                transaction.getTotalOutput(), transaction.getRemainingAmount(), transaction.getReceiver().getAddress(),
                transaction.getReceiver().getName(), transaction.getSender().getAddress(), transaction.getSender().getName(),
                transaction.getCreatedAt(), transaction.getSignedAt(), transaction.getStatus());
    }

    @Getter
    @AllArgsConstructor
    private static class UnSpentNote {
        private String open;
        private String tokenAddress;
        private String tokenId;
        private String amount;
        private String addr;
        private String commitment;
        private String index;

        public static UnSpentNote from(Note fromUnSpentNote) {
            if(fromUnSpentNote == null) return null;
            else {
                return new UnSpentNote(fromUnSpentNote.getOpen(), fromUnSpentNote.getTokenAddress(), fromUnSpentNote.getTokenId(), fromUnSpentNote.getAmount(), fromUnSpentNote.getAddr(), fromUnSpentNote.getCommitment(), fromUnSpentNote.getIndex());
            }
        }
    }
}