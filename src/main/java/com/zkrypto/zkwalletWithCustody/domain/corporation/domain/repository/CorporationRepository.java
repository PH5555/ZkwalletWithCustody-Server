package com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CorporationRepository extends JpaRepository<Corporation, UUID> {

    Optional<Corporation> findCorporationByCorporationId(String corporationId);

    @Query("select corporation from Corporation corporation left join fetch corporation.members")
    List<Corporation> findAllWithMembers();

    boolean existsCorporationByName(String name);

    Optional<Corporation> findCorporationsByAddress(String address);
}
