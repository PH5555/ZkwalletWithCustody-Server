package com.zkrypto.zkwalletWithCustody.transaction;

import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.WalletCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.repository.MemberRepository;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.repository.NoteRepository;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionUpdateCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.response.TransactionResponse;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.service.TransactionService;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Type;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CorporationService corporationService;

    @Autowired
    private CorporationRepository corporationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NoteRepository noteRepository;

//    @Test
//    void 트랜잭션_생성() throws Exception {
//        Corporation corporation1 = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
//        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("짭크립토"));
//        Member member = new Member();
//        member.setName("동현");
//        member.setCorporation(corporation1);
//        member.setPassword(passwordEncoder.encode("1234"));
//
//        Note note = new Note();
//        noteRepository.save(note);
//
//        memberRepository.save(member);
//
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation1.getCorporationId()));
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation2.getCorporationId()));
//
//        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporation2.getCorporationId()).get();
//
//        TransactionCreationCommand command = new TransactionCreationCommand(0,0,note.getNoteId(),0,0,0,0,0,corporation.getAddress(),"1234");
//        Transaction transaction = transactionService.createTransaction(member.getMemberId(), command);
//        Assertions.assertThat(transaction.getFromUnSpentNote().getNoteId()).isEqualTo(note.getNoteId());
//    }
//
//    @Test
//    void 트랜잭션_생성_비밀번호_불일치() throws Exception {
//        Corporation corporation1 = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
//        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("짭크립토"));
//        Member member = new Member();
//        member.setName("동현");
//        member.setCorporation(corporation1);
//        member.setPassword(passwordEncoder.encode("1234"));
//
//        memberRepository.save(member);
//
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation1.getCorporationId()));
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation2.getCorporationId()));
//
//        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporation2.getCorporationId()).get();
//
//        TransactionCreationCommand command = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress(),"12334");
//        Assertions.assertThatThrownBy(() -> transactionService.createTransaction(member.getMemberId(), command)).hasMessageContaining("비밀번호가 불일치합니다.");
//    }
//
//    @Test
//    void 트랜잭션_생성_주소_불일치() throws Exception {
//        Corporation corporation1 = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
//        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("짭크립토"));
//        Member member = new Member();
//        member.setName("동현");
//        member.setCorporation(corporation1);
//        member.setPassword(passwordEncoder.encode("1234"));
//
//        memberRepository.save(member);
//
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation1.getCorporationId()));
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation2.getCorporationId()));
//
//        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporation2.getCorporationId()).get();
//
//        TransactionCreationCommand command = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress() + "22","1234");
//        Assertions.assertThatThrownBy(() -> transactionService.createTransaction(member.getMemberId(), command)).hasMessageContaining("해당 주소를 가진 법인이 없습니다");
//    }
//
//    @Test
//    void 트랜잭션_가져오기_승인대기() throws Exception {
//        Corporation corporation1 = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
//        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("짭크립토"));
//        Member member = new Member();
//        member.setName("동현");
//        member.setCorporation(corporation1);
//        member.setRole(Role.ROLE_USER);
//        member.setPassword(passwordEncoder.encode("1234"));
//
//        memberRepository.save(member);
//
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation1.getCorporationId()));
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation2.getCorporationId()));
//
//        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporation2.getCorporationId()).get();
//
//        TransactionCreationCommand command = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress(),"1234");
//        Transaction transaction = transactionService.createTransaction(member.getMemberId(), command);
//
//        TransactionCreationCommand command2 = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress(),"1234");
//        Transaction transaction2 = transactionService.createTransaction(member.getMemberId(), command2);
//        transactionService.updateTransaction(new TransactionUpdateCommand(transaction2.getId()));
//
//        List<TransactionResponse> transactions = transactionService.getTransactions(member.getMemberId(), Status.NONE, Type.SEND);
//        Assertions.assertThat(transactions.get(0).getTransactionId()).isEqualTo(transaction.getId());
//        Assertions.assertThat(transactions.size()).isEqualTo(1);
//    }
//
//    @Test
//    void 트랜잭션_가져오기_승인완료() throws Exception {
//        Corporation corporation1 = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
//        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("짭크립토"));
//        Member member = new Member();
//        member.setName("동현");
//        member.setCorporation(corporation1);
//        member.setRole(Role.ROLE_USER);
//        member.setPassword(passwordEncoder.encode("1234"));
//
//        memberRepository.save(member);
//
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation1.getCorporationId()));
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation2.getCorporationId()));
//
//        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporation2.getCorporationId()).get();
//
//        TransactionCreationCommand command = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress(),"1234");
//        Transaction transaction = transactionService.createTransaction(member.getMemberId(), command);
//
//        TransactionCreationCommand command2 = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress(),"1234");
//        Transaction transaction2 = transactionService.createTransaction(member.getMemberId(), command2);
//        transactionService.updateTransaction(new TransactionUpdateCommand(transaction2.getId()));
//
//        List<TransactionResponse> transactions = transactionService.getTransactions(member.getMemberId(), Status.DONE, Type.SEND);
//        Assertions.assertThat(transactions.get(0).getTransactionId()).isEqualTo(transaction2.getId());
//        Assertions.assertThat(transactions.size()).isEqualTo(1);
//    }
//
//    @Test
//    void 받은_트랜잭션_가져오기_승인완료() throws Exception {
//        Corporation corporation1 = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
//        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("짭크립토"));
//        Member member = new Member();
//        member.setName("동현");
//        member.setCorporation(corporation1);
//        member.setRole(Role.ROLE_USER);
//        member.setPassword(passwordEncoder.encode("1234"));
//
//        Member member2 = new Member();
//        member2.setName("동현2");
//        member2.setCorporation(corporation2);
//        member2.setRole(Role.ROLE_USER);
//        member2.setPassword(passwordEncoder.encode("1234"));
//
//        memberRepository.save(member);
//        memberRepository.save(member2);
//
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation1.getCorporationId()));
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation2.getCorporationId()));
//
//        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporation2.getCorporationId()).get();
//        Corporation corporation3 = corporationRepository.findCorporationByCorporationId(corporation1.getCorporationId()).get();
//
//        TransactionCreationCommand command = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress(),"1234");
//        Transaction transaction = transactionService.createTransaction(member.getMemberId(), command);
//
//        TransactionCreationCommand command2 = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress(),"1234");
//        Transaction transaction2 = transactionService.createTransaction(member.getMemberId(), command2);
//        transactionService.updateTransaction(new TransactionUpdateCommand(transaction2.getId()));
//
//
//        TransactionCreationCommand command3 = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation3.getAddress(),"1234");
//        Transaction transaction3 = transactionService.createTransaction(member2.getMemberId(), command3);
//        transactionService.updateTransaction(new TransactionUpdateCommand(transaction3.getId()));
//
//        List<TransactionResponse> transactions = transactionService.getTransactions(member.getMemberId(), Status.DONE, Type.RECEIVE);
//        Assertions.assertThat(transactions.get(0).getTransactionId()).isEqualTo(transaction3.getId());
//        Assertions.assertThat(transactions.size()).isEqualTo(1);
//    }
//
//    @Test
//    void 트랜잭션_어드민() throws Exception {
//        Corporation corporation1 = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
//        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("짭크립토"));
//        Member member = new Member();
//        member.setName("동현");
//        member.setCorporation(corporation1);
//        member.setRole(Role.ROLE_USER);
//        member.setPassword(passwordEncoder.encode("1234"));
//
//        Member member2 = new Member();
//        member2.setName("동현2");
//        member2.setCorporation(corporation2);
//        member2.setRole(Role.ROLE_USER);
//        member2.setPassword(passwordEncoder.encode("1234"));
//
//        memberRepository.save(member);
//        memberRepository.save(member2);
//
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation1.getCorporationId()));
//        corporationService.createCorporationWallet(new WalletCreationCommand(corporation2.getCorporationId()));
//
//        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporation2.getCorporationId()).get();
//        Corporation corporation3 = corporationRepository.findCorporationByCorporationId(corporation1.getCorporationId()).get();
//
//        TransactionCreationCommand command = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress(),"1234");
//        Transaction transaction = transactionService.createTransaction(member.getMemberId(), command);
//
//        TransactionCreationCommand command2 = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation.getAddress(),"1234");
//        Transaction transaction2 = transactionService.createTransaction(member.getMemberId(), command2);
//        transactionService.updateTransaction(new TransactionUpdateCommand(transaction2.getId()));
//
//        TransactionCreationCommand command3 = new TransactionCreationCommand(0,0,0,0,0,0,0,0,corporation3.getAddress(),"1234");
//        Transaction transaction3 = transactionService.createTransaction(member2.getMemberId(), command3);
//        transactionService.updateTransaction(new TransactionUpdateCommand(transaction3.getId()));
//
//
//        List<TransactionResponse> transactions = transactionService.getTransactions(null, null, null);
//        Assertions.assertThat(transactions.size()).isEqualTo(3);
//    }
}
