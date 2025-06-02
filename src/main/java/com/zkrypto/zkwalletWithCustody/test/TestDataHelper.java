package com.zkrypto.zkwalletWithCustody.test;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.SignUpCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.service.AuthService;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestDataHelper implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Member member = new Member();
        member.setName("관리자");
        member.setRole(Role.ROLE_ADMIN);
        member.setLoginId("zkrypto");
        member.setPassword(passwordEncoder.encode("1234"));
        memberRepository.save(member);
    }
}
