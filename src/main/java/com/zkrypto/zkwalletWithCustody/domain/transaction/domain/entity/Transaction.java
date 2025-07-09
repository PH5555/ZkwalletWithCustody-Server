package com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int fromPrivateAmount;
    private int fromPublicAmount;

    @OneToOne
    @JoinColumn(name = "note_id")
    private Note fromUnSpentNote;

    private int totalInput;
    private int toPublicAmount;
    private int toPrivateAmount;
    private int totalOutput;
    private int remainingAmount;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime signedAt;
    private BigInteger blockNumber;
    private String transactionHash;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Corporation sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Corporation receiver;

    private Transaction(int remainingAmount, Corporation receiver, Corporation sender, int totalOutput, int toPrivateAmount, int toPublicAmount, int totalInput, Note note, int fromPublicAmount, int fromPrivateAmount) {
        this.remainingAmount = remainingAmount;
        this.receiver = receiver;
        this.sender = sender;
        this.totalOutput = totalOutput;
        this.toPrivateAmount = toPrivateAmount;
        this.toPublicAmount = toPublicAmount;
        this.totalInput = totalInput;
        this.fromUnSpentNote = note;
        this.fromPublicAmount = fromPublicAmount;
        this.fromPrivateAmount = fromPrivateAmount;
        this.status = Status.NONE;
    }

    public static Transaction create(TransactionCreationCommand transactionCreationCommand, Corporation sender, Corporation receiver, Note note) {
        return new Transaction(
                transactionCreationCommand.getRemainingAmount(),
                receiver,
                sender,
                transactionCreationCommand.getTotalOutput(),
                transactionCreationCommand.getToPrivateAmount(),
                transactionCreationCommand.getToPublicAmount(),
                transactionCreationCommand.getTotalInput(),
                note,
                transactionCreationCommand.getFromPublicAmount(),
                transactionCreationCommand.getFromPrivateAmount());
    }

    public void update(BigInteger blockNumber, String transactionHash) {
        this.status = Status.DONE;
        this.blockNumber = blockNumber;
        this.transactionHash = transactionHash;
        this.signedAt = LocalDateTime.now();
    }
}
