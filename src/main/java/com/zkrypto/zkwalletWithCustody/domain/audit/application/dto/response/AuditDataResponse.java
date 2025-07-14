package com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.response;

import com.zkrypto.zkwalletWithCustody.domain.audit.domain.entity.AuditData;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuditDataResponse {
    private String transactionHash;
    private String fromAddress;
    private LocalDateTime time;

    public static AuditDataResponse from(AuditData auditData, String address) {
        return new AuditDataResponse(auditData.getTransactionHash(), address, auditData.getSignedAt());
    }
}
