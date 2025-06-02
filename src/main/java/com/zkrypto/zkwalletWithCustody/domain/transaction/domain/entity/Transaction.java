package com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int fromPrivateAmount;
    private int fromPublicAmount;
    private int unSpentNote;
    private int totalInput;
    private int toPublicAmount;
    private int toPrivateAmount;
    private int totalOutput;
    private int remainingAmount;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime signedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Corporation sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Corporation receiver;

    private Transaction(int remainingAmount, Corporation receiver, Corporation sender, int totalOutput, int toPrivateAmount, int toPublicAmount, int totalInput, int unSpentNote, int fromPublicAmount, int fromPrivateAmount) {
        this.remainingAmount = remainingAmount;
        this.receiver = receiver;
        this.sender = sender;
        this.totalOutput = totalOutput;
        this.toPrivateAmount = toPrivateAmount;
        this.toPublicAmount = toPublicAmount;
        this.totalInput = totalInput;
        this.unSpentNote = unSpentNote;
        this.fromPublicAmount = fromPublicAmount;
        this.fromPrivateAmount = fromPrivateAmount;
        this.status = Status.NONE;
    }

    public static Transaction create(TransactionCreationCommand transactionCreationCommand, Corporation sender, Corporation receiver) {
        return new Transaction(transactionCreationCommand.getRemainingAmount(), receiver, sender,
                transactionCreationCommand.getTotalOutput(), transactionCreationCommand.getToPrivateAmount(), transactionCreationCommand.getToPublicAmount(),
                transactionCreationCommand.getTotalInput(), transactionCreationCommand.getUnSpentNote(), transactionCreationCommand.getFromPublicAmount(), transactionCreationCommand.getToPrivateAmount());
    }
}
