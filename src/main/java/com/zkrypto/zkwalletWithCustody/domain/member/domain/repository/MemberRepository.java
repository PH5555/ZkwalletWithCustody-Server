package com.zkrypto.zkwalletWithCustody.domain.member.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    boolean existsByMemberId(UUID memberId);

    boolean existsByName(String name);

    boolean existsByLoginId(String loginId);

    Optional<Member> findMemberByLoginId(String loginId);

    Optional<Member> findMemberByMemberId(UUID memberId);

    @Query("select member from Member member left join fetch member.corporation where member.memberId = :memberId")
    Optional<Member> findMemberByMemberIdWithCorporation(@Param(value = "memberId") UUID memberId);

    @Query("select count(member) from Member member where member.corporation = :corporation")
    int findMemberCountByCorporation(@Param(value = "corporation") Corporation corporation);
}
