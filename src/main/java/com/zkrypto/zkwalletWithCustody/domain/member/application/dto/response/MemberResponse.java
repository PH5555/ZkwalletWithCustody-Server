package com.zkrypto.zkwalletWithCustody.domain.member.application.dto.response;

import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class MemberResponse {
    private UUID id;
    private String name;
    private String position;

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getMemberId(), member.getName(), member.getPosition());
    }
}
