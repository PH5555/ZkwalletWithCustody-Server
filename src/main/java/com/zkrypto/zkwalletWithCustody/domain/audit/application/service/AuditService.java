package com.zkrypto.zkwalletWithCustody.domain.audit.application.service;

import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.request.AuditCommand;
import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.response.AuditDataResponse;
import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.response.AuditResultResponse;
import com.zkrypto.zkwalletWithCustody.domain.audit.domain.constant.AuditKey;
import com.zkrypto.zkwalletWithCustody.domain.audit.domain.entity.AuditData;
import com.zkrypto.zkwalletWithCustody.domain.audit.domain.repository.AuditDataRepository;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import com.zkrypto.zkwalletWithCustody.domain.note.application.service.NoteService;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.AffinePoint;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.MiMC7;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.TwistedEdwardsCurve;
import com.zkrypto.zkwalletWithCustody.global.web3.Groth16AltBN128Mixer;
import com.zkrypto.zkwalletWithCustody.global.web3.Web3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditDataRepository auditDataRepository;
    private final CorporationService corporationService;

    /**
     * 모든 감사 데이터 조회 메서드
     */
    public List<AuditDataResponse> getAuditData() {
        List<AuditData> auditData = auditDataRepository.findAll();
        return auditData.stream().map(this::toAuditDateResponse).toList();
    }

    private AuditDataResponse toAuditDateResponse(AuditData auditData) {
        String address = corporationService.enaToAddress(auditData.getEna());
        return AuditDataResponse.from(auditData, address);
    }

    /**
     * 감사 데이터 저장 메서드
     */
    public void createAuditData(Groth16AltBN128Mixer.LogZkTransferEventResponse event) {
        AuditData auditData = AuditData.from(event);
        auditDataRepository.save(auditData);
    }

    /**
     * 감사 진행 메서드
     */
    @Transactional
    public AuditResultResponse processAudit(AuditCommand auditCommand) {
        // 감사 데이터 조회
        AuditData auditData = auditDataRepository.findByTransactionHash(auditCommand.getTransactionHash())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 데이터가 없습니다."));

        // 노트 복원
        Note note = Note.recoverNote(auditData.getCt().stream().map(BigInteger::new).toList(), new BigInteger(auditData.getCom()), new BigInteger(auditData.getNumLeaves()), auditCommand.getAuditorKey(), Role.ROLE_AUDIT);

        // 노트 유효성 확인
        if(!note.isOwner()) {
            throw new IllegalArgumentException("감사를 실패했습니다.");
        }

        return toAuditResultResponse(note);
    }

    private AuditResultResponse toAuditResultResponse(Note note) {
        String address = corporationService.enaToAddress(note.getAddr());
        return AuditResultResponse.from(note, address);
    }

    /**
     * 감사키 생성 메서드
     */
    public void generateAuditKey() {
        // 감사키 생성
        AuditKey auditKey = AuditKey.keyGen();
        log.info("x: " + "0x" + auditKey.getPk().getX().toString(16));
        log.info("y: " + "0x" + auditKey.getPk().getY().toString(16));
    }

    private BigInteger mod(BigInteger value, BigInteger mod) {
        if(value.compareTo(BigInteger.ZERO) > 0) {
            return value.mod(mod);
        } else {
            return ((value.mod(mod)).add(mod)).mod(mod);
        }
    }
}
