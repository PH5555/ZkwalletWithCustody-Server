package com.zkrypto.zkwalletWithCustody.domain.audit.domain.entity;

import com.zkrypto.zkwalletWithCustody.global.util.StringListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
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
}
