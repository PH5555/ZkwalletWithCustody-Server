package com.zkrypto.zkwalletWithCustody.test;

import com.zkrypto.zkwalletWithCustody.domain.audit.application.service.AuditService;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestDataHelper implements ApplicationRunner {
    private final AuditService auditService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        auditService.createAuditor();
    }
}
