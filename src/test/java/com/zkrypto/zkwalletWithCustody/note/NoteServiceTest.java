package com.zkrypto.zkwalletWithCustody.note;

import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.repository.MemberRepository;
import com.zkrypto.zkwalletWithCustody.domain.note.application.dto.response.NoteResponse;
import com.zkrypto.zkwalletWithCustody.domain.note.application.service.NoteService;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.repository.NoteRepository;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.List;

@SpringBootTest
public class NoteServiceTest {
    @Autowired
    private CorporationService corporationService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NoteService noteService;
    @Autowired
    private MemberRepository memberRepository;

//    @Test
//    void 노트_사용후_조회() {
//        Corporation corporation = corporationService.createCorporation(new CorporationCreationCommand("지크립토토"));
//        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("지크립토토토"));
//
//        Member member = new Member();
//        member.setCorporation(corporation);
//        memberRepository.save(member);
//
//        Note note = new Note();
//        note.setIsSpent(false);
//        note.setCorporation(corporation);
//        noteRepository.save(note);
//
//        Transaction transaction1 = new Transaction();
//        transaction1.setSender(corporation);
//        transaction1.setReceiver(corporation2);
//        transaction1.setFromUnSpentNote(note);
//        transactionRepository.save(transaction1);
//
//        List<NoteResponse> corporationNotes = noteService.getCorporationNotes(member.getMemberId());
//        Assertions.assertThat(corporationNotes.size()).isEqualTo(0);
//    }
//
//    @Test
//    void 노트_소비전_조회() {
//        Corporation corporation = corporationService.createCorporation(new CorporationCreationCommand("지크립토토"));
//        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("지크립토토토"));
//
//        Member member = new Member();
//        member.setCorporation(corporation);
//        memberRepository.save(member);
//
//        Note note = new Note();
//        note.setIsSpent(false);
//        note.setCorporation(corporation);
//        noteRepository.save(note);
//
//        Transaction transaction1 = new Transaction();
//        transaction1.setSender(corporation);
//        transaction1.setReceiver(corporation2);
//        transactionRepository.save(transaction1);
//
//        List<NoteResponse> corporationNotes = noteService.getCorporationNotes(member.getMemberId());
//        Assertions.assertThat(corporationNotes.size()).isEqualTo(1);
//    }
//
//    @Test
//    void 노트_소비후_조회() {
//        Corporation corporation = corporationService.createCorporation(new CorporationCreationCommand("지크립토토"));
//        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("지크립토토토"));
//
//        Member member = new Member();
//        member.setCorporation(corporation);
//        memberRepository.save(member);
//
//        Note note = new Note();
//        note.setIsSpent(true);
//        note.setCorporation(corporation);
//        noteRepository.save(note);
//
//        Transaction transaction1 = new Transaction();
//        transaction1.setSender(corporation);
//        transaction1.setReceiver(corporation2);
//        transactionRepository.save(transaction1);
//
//        List<NoteResponse> corporationNotes = noteService.getCorporationNotes(member.getMemberId());
//        Assertions.assertThat(corporationNotes.size()).isEqualTo(0);
//    }
}
