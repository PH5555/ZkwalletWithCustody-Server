package com.zkrypto.zkwalletWithCustody.domain.auth.application.service;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.ReissueCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.SignInCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.SignUpCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.response.AuthTokenResponse;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.repository.MemberRepository;
import com.zkrypto.zkwalletWithCustody.global.jwt.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenHandler jwtTokenHandler;
    private final CorporationRepository corporationRepository;

    /**
     * 회원가입 메서드
     */
    @Transactional
    public void signUp(SignUpCommand signUpCommand) {
        // 이미 있는 회원인지 확인
        if(memberRepository.existsByLoginId(signUpCommand.getLoginId())) {
            throw new RuntimeException("아이디가 중복됩니다.");
        }

        // 존재하는 법인인지 확인
        Corporation corporation = corporationRepository.findCorporationByCorporationId(signUpCommand.getCorporationId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 법인ID입니다."));

        // 비밀번호 암호화
        String hashedPassword = passwordEncoder.encode(signUpCommand.getPassword());

        // 유저 정보 저장
        Member member = Member.join(signUpCommand, hashedPassword, corporation);
        memberRepository.save(member);
    }

    /**
     * 로그인 메서드
     */
    @Transactional
    public AuthTokenResponse signIn(SignInCommand signInCommand) {
        // 아이디 확인
        Member member = memberRepository.findMemberByLoginId((signInCommand.getLoginId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 비밀번호 확인
        if(!passwordEncoder.matches(signInCommand.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 불일치합니다.");
        }

        // 토큰 발급
        AuthTokenResponse tokenResponse = jwtTokenHandler.generate(member);

        // 리프레쉬 토큰 저장
        member.storeRefreshToken(tokenResponse.getRefreshToken());
        memberRepository.save(member);

        // 토큰 리턴
        return tokenResponse;
    }

    @Transactional
    public AuthTokenResponse reissue(ReissueCommand reissueCommand) {
        // 멤버 존재 확인
        Member member = memberRepository.findMemberByMemberId(reissueCommand.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 토큰 일치 확인
        validate(reissueCommand.getRefreshToken(), member);

        // 토큰 재발급
        AuthTokenResponse tokenResponse = jwtTokenHandler.generate(member);

        // 토큰 저장
        member.storeRefreshToken(tokenResponse.getRefreshToken());
        return tokenResponse;
    }

    @Transactional
    public void signOut(UUID memberId) {
        // 멤버 존재 확인
        Member member = memberRepository.findMemberByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        member.signOut();
    }

    private void validate(String refreshToken, Member member) {
        // 유효한 리프레쉬인지 확인
        jwtTokenHandler.validateRefreshToken(refreshToken);

        // 유저와 일치하는 리프레쉬토큰인지 확인
        if (!member.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("리프레시 토큰이 일치하지 않습니다.");
        }
    }
}
