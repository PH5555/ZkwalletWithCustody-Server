package com.zkrypto.zkwalletWithCustody.domain.audit.application.service;

import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.request.AuditCommand;
import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.response.AuditDataResponse;
import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.response.AuditResultResponse;
import com.zkrypto.zkwalletWithCustody.domain.audit.domain.constant.AuditKey;
import com.zkrypto.zkwalletWithCustody.domain.audit.domain.entity.AuditData;
import com.zkrypto.zkwalletWithCustody.domain.audit.domain.repository.AuditDataRepository;
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
    private final NoteService noteService;
    private final Web3Service web3Service;

    @Value("${contract.mixer.address}")
    private String contractAddress;

    @Value("${ethereum.privateKey}")
    private String privateKey;
    /**
     * 모든 감사 데이터 조회 메서드
     */
    public List<AuditDataResponse> getAuditData() {
        List<AuditData> auditData = auditDataRepository.findAll();
        return auditData.stream().map(AuditDataResponse::from).toList();
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
        AffinePoint c0 = new AffinePoint(new BigInteger(auditData.getCt().get(0)), new BigInteger(auditData.getCt().get(1)));
        AffinePoint c1 = new AffinePoint(new BigInteger(auditData.getCt().get(2)), new BigInteger(auditData.getCt().get(3)));
        AffinePoint c2 = new AffinePoint(new BigInteger(auditData.getCt().get(4)), new BigInteger(auditData.getCt().get(5)));
        TwistedEdwardsCurve curve = new TwistedEdwardsCurve();
        AffinePoint curveC0 = curve.computeScalarMul(c0, new BigInteger(auditCommand.getAuditorKey()));
        AffinePoint curveK = curve.subAffinePoint(c2, curveC0);

        MiMC7 mimc7 = new MiMC7();
        List<BigInteger> ret = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);
        auditData.getCt().stream().skip(6).forEach((e) -> {
            BigInteger hash = mimc7.hash(curveK.getX(), BigInteger.valueOf(counter.getAndIncrement()));
            ret.add(mod(new BigInteger(e).subtract(hash), new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617")));
        });
        Note note = Note.from(ret, auditData.getCom(), new BigInteger(auditData.getNumLeaves()));

        // 노트 유효성 확인
        if(!noteService.isOwner(note)) {
            throw new IllegalArgumentException("감사를 실패했습니다.");
        }
        return AuditResultResponse.from(note);
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
