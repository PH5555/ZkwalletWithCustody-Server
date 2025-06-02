package com.zkrypto.zkwalletWithCustody.transaction;

import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.WalletCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.service.TransactionService;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CorporationService corporationService;
    @Autowired
    private CorporationRepository corporationRepository;


    @Test
    void 트랜잭션_생성() throws Exception {
        Corporation corporation1 = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("짭크립토"));
        Member member = new Member();
        member.setName("동현");
        member.setCorporation(corporation1);
        member.setPassword("1234");

        corporationService.createCorporationWallet(new WalletCreationCommand(corporation1.getCorporationId()));
        corporationService.createCorporationWallet(new WalletCreationCommand(corporation2.getCorporationId()));

        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporation2.getCorporationId()).get();

        TransactionCreationCommand command = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress(),"1234");
        Transaction transaction = transactionService.createTransaction(member.getMemberId(), command);
        Assertions.assertThat(transaction.getReceiver().getCorporationId()).isEqualTo(corporation2.getCorporationId());
    }

    @Test
    void 트랜잭션_생성_비밀번호_불일치() throws Exception {
        Corporation corporation1 = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("짭크립토"));
        Member member = new Member();
        member.setName("동현");
        member.setCorporation(corporation1);
        member.setPassword("1234");

        corporationService.createCorporationWallet(new WalletCreationCommand(corporation1.getCorporationId()));
        corporationService.createCorporationWallet(new WalletCreationCommand(corporation2.getCorporationId()));

        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporation2.getCorporationId()).get();

        TransactionCreationCommand command = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress(),"12334");
        Assertions.assertThatThrownBy(() -> transactionService.createTransaction(member.getMemberId(), command)).hasMessageContaining("비밀번호가 불일치합니다.");
    }

    @Test
    void 트랜잭션_생성_주소_불일치() throws Exception {
        Corporation corporation1 = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("짭크립토"));
        Member member = new Member();
        member.setName("동현");
        member.setCorporation(corporation1);
        member.setPassword("1234");

        corporationService.createCorporationWallet(new WalletCreationCommand(corporation1.getCorporationId()));
        corporationService.createCorporationWallet(new WalletCreationCommand(corporation2.getCorporationId()));

        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporation2.getCorporationId()).get();

        TransactionCreationCommand command = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress() + "22","1234");
        Assertions.assertThatThrownBy(() -> transactionService.createTransaction(member.getMemberId(), command)).hasMessageContaining("해당 주소를 가진 법인이 없습니다");
    }
}
