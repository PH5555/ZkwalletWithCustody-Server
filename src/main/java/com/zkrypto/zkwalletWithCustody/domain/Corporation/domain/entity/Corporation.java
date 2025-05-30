package com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Corporation {
    @Id
    private String corporationId;

    private String name;
    private String address;
    private String salt;

    @CreatedDate
    private LocalDateTime createdAt;

    private String secretKey;
}
