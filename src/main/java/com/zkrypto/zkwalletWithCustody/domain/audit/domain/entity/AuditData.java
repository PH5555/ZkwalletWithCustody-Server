package com.zkrypto.zkwalletWithCustody.domain.audit.domain.entity;

import com.zkrypto.zkwalletWithCustody.global.util.StringListConverter;
import com.zkrypto.zkwalletWithCustody.global.web3.Groth16AltBN128Mixer;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuditData {
    @Id
    private String transactionHash;

    private String nullifier;
    private String com;
    private String numLeaves;

    @Convert(converter = StringListConverter.class)
    private List<String> ct;
    private String ena;
    private LocalDateTime signedAt;

    public static AuditData from(Groth16AltBN128Mixer.LogZkTransferEventResponse event) {
        return new AuditData(
                event.log.getTransactionHash(),
                event.nullifier.toString(),
                event.com.toString(),
                event.numLeaves.toString(),
                event.ct.stream().map(BigInteger::toString).toList(),
                event.ena.toString(),
                LocalDateTime.now()
        );
    }
}
