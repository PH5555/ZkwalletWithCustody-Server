package com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CorporationRepository extends JpaRepository<Corporation, UUID> {

    Optional<Corporation> findCorporationByCorporationId(String corporationId);

    @Query("select corporation from Corporation corporation left join fetch corporation.members")
    List<Corporation> findAllWithMembers();

    @Query("select corporation from Corporation corporation left join fetch corporation.members where corporation.corporationId = :corporationId")
    Optional<Corporation> findWithMembersByCorporationId(@Param(value = "corporationId") String corporationId);

    boolean existsCorporationByName(String name);

    Optional<Corporation> findCorporationByAddress(String address);

    @Query("select corporation from Member member left join member.corporation corporation where member.memberId = :memberId")
    Optional<Corporation> findCorporationByMember(@Param(value = "memberId") UUID memberId);

    Optional<Corporation> findCorporationByName(String name);
}
