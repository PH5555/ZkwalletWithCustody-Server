package com.zkrypto.zkwalletWithCustody.domain.member.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import jnr.a64asm.Mem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    boolean existsByMemberId(UUID memberId);

    boolean existsByName(String name);

    boolean existsByLoginId(String loginId);

    Optional<Member> findMemberByLoginId(String loginId);

    Optional<Member> findMemberByMemberId(UUID memberId);

    @Query("select member from Member member left join fetch member.corporation")
    Optional<Member> findMemberByMemberIdWithCorporation(UUID memberId);
}
