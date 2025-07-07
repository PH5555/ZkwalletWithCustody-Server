package com.zkrypto.zkwalletWithCustody.test;

import com.zkrypto.zkwalletWithCustody.domain.audit.application.service.AuditService;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.WalletCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletCreationResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.repository.MemberRepository;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.service.TransactionService;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestDataHelper implements ApplicationRunner {
    private final TransactionService transactionService;
    private final CorporationService corporationService;
    private final CorporationRepository corporationRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Corporation corporation1 = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("한양대학교"));

        Member member = new Member();
        member.setName("동현");
        member.setCorporation(corporation1);
        member.setPassword(passwordEncoder.encode("1234"));
        member.setPosition("CEO");
        member.setLoginId("1234");
        member.setRole(Role.ROLE_USER);

        memberRepository.save(member);

        WalletCreationResponse corporationWallet = corporationService.createCorporationWallet(new WalletCreationCommand(corporation1.getCorporationId()));
        WalletCreationResponse corporationWallet1 = corporationService.createCorporationWallet(new WalletCreationCommand(corporation2.getCorporationId()));

        log.info("지크립토 : " + "0x" + corporationWallet.getPrivateKey());
        log.info("한양대학교 : " + "0x" + corporationWallet1.getPrivateKey());

        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporation2.getCorporationId()).get();

        TransactionCreationCommand command = new TransactionCreationCommand(0,1,0,1,0,1,1,0,corporation.getAddress(),"1234");
        transactionService.createTransaction(member.getMemberId(), command);
    }
}
