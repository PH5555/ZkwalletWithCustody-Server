package com.zkrypto.zkwalletWithCustody.domain.note.application.service;

import com.zkrypto.zkwalletWithCustody.domain.corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import com.zkrypto.zkwalletWithCustody.domain.note.application.dto.response.NoteResponse;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.repository.NoteRepository;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository.TransactionRepository;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.AffinePoint;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.MiMC7;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.TwistedEdwardsCurve;
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.AESUtils;
import com.zkrypto.zkwalletWithCustody.global.web3.Groth16AltBN128Mixer;
import com.zkrypto.zkwalletWithCustody.global.web3.Web3Service;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final CorporationRepository corporationRepository;
    private final Web3Service web3Service;
    private final TransactionRepository transactionRepository;
    private final CorporationService corporationService;

    @Value("${contract.mixer.address}")
    private String contractAddress;

    @Value("${ethereum.privateKey}")
    private String privateKey;

    public void saveNote(Note note) {
        noteRepository.save(note);
    }

    /**
     * 노트 조회 메서드
     */
    public List<NoteResponse> getCorporationNotes(UUID memberId) {
        // 법인 확인
        Corporation corporation = corporationRepository.findCorporationByMember(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 법인입니다."));

        // 법인의 소비하지 않은 모든 노트 가져오기
        List<Note> notes = noteRepository.findNotSpendNotesByCorporation(corporation.getCorporationId());

        // 사용한적 없는 노트만 반환
        return notes.stream().filter(note -> !transactionRepository.existsTransactionByFromUnSpentNote(note)).map(this::toNoteResponse).toList();
    }

    private NoteResponse toNoteResponse(Note note) {
        String address = corporationService.enaToAddress(note.getAddr());
        return NoteResponse.from(note, address);
    }

    /**
     * zktransfer에서 note 사용후, note 업데이트하는 메서드
     */
    @Transactional
    public void updateNoteSpend(UUID noteId, Corporation corporation) throws Exception {
        // note 확인
        Note note = noteRepository.findNoteByNoteId(noteId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 노트입니다"));

        // usk 복원
        String usk = AESUtils.decrypt(corporation.getSecretKey(), corporation.getSalt());

        // 노트 사용 확인
        MiMC7 mimc7 = new MiMC7();
        BigInteger nf = mimc7.hash(new BigInteger(note.getCommitment()), new BigInteger(usk));
        Groth16AltBN128Mixer smartContract = web3Service.loadContract(privateKey, contractAddress);

        // 노트 사용 여부 업데이트
        if(smartContract.isNullified(nf).send()) {
            note.setNoteSpend();
        }
    }


    /***
     * 트랜잭션 ct, commitment, numleaves 정보로 노트 생성하는 메서드
     */
    public Note getNote(List<BigInteger> ct, BigInteger commitment, BigInteger numLeaves, Corporation corporation) throws Exception {
        // usk 복호화
        String usk = AESUtils.decrypt(corporation.getSecretKey(), corporation.getSalt());

        // note 복원
        Note note = Note.recoverNote(ct, commitment, numLeaves, usk, Role.ROLE_USER);
        note.setCorporation(corporation);
        return note;
    }
}
