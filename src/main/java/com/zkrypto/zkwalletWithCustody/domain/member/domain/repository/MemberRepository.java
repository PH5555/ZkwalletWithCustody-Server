package com.zkrypto.zkwalletWithCustody.domain.member.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    boolean existsByMemberId(UUID memberId);

    boolean existsByName(String name);

    boolean existsByLoginId(String loginId);

    Optional<Member> findMemberByLoginId(String loginId);
}
