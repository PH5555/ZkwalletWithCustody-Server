package com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.request;

import lombok.Getter;

@Getter
public class AuditCommand {
    private String auditorKey;
    private String transactionHash;
}
