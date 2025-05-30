package com.zkrypto.zkwalletWithCustody.global.jwt;

import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.response.AuthTokenResponse;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 5;  // 5시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    public AuthTokenResponse generate(Member member) {
        Date accessTokenExpiredAt = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = member.getMemberId().toString();
        String accessToken = jwtTokenProvider.generateAccessToken(subject, member.getRole(),accessTokenExpiredAt);
        String refreshToken = jwtTokenProvider.generateRefreshToken(refreshTokenExpiredAt);

        return new AuthTokenResponse(accessToken, refreshToken);
    }

    public void validateRefreshToken(String refreshToken) {
        jwtTokenProvider.verifyToken(refreshToken);
    }
}
