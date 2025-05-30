package com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.entity.Corporation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CorporationRepository extends JpaRepository<Corporation, UUID> {

    Optional<Corporation> findCorporationByCorporationId(String corporationId);
}
