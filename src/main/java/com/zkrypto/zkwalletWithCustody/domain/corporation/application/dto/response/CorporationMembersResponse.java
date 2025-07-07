package com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CorporationMembersResponse {
    private String name;
    private String position;
    private LocalDateTime registrationDate;
    private String didAddress;
}
