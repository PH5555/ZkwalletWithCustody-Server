package com.zkrypto.zkwalletWithCustody.domain.audit.application.service;

import com.zkrypto.zkwalletWithCustody.domain.audit.domain.constant.AuditKey;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    /***
     * 감사자 생성 메서드
     */
    public void createAuditor() {
        // auditor 키 생성
        AuditKey auditor = AuditKey.keyGen();

        // 키 등록

    }
}
