package com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.response;

import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuditResultResponse {
    private String toAddr;
    private String amount;
    private String tokenAddress;
    private String tokenId;

    public static AuditResultResponse from(Note note, String address) {
        return new AuditResultResponse(address, note.getAmount(), note.getTokenAddress(), note.getTokenId());
    }
}
