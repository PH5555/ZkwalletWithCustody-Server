package com.zkrypto.zkwalletWithCustody.domain.member.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID memberId;

    private String name;
    private String password;
    private String position;
    private String role;

    @CreatedDate
    private LocalDateTime createdAt;
}
