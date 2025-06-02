package com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.response;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.entity.Corporation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CorporationResponse {
    private String corporationId;
    private String name;
    private int registerStatus;
    private LocalDateTime createdAt;

    public static CorporationResponse from(Corporation corporation) {
        return new CorporationResponse(corporation.getCorporationId(), corporation.getName(), corporation.getMembers().size(), corporation.getCreatedAt());
    }
}
