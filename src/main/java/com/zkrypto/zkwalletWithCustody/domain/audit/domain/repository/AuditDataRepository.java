package com.zkrypto.zkwalletWithCustody.domain.audit.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.audit.domain.entity.AuditData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuditDataRepository extends JpaRepository<AuditData, String> {
    Optional<AuditData> findByTransactionHash(String transactionHash);
}
