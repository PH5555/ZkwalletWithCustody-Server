package com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.entity;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.request.CorporationCreationCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Corporation {
    @Id
    private String corporationId;

    private String name;
    private String address;
    private String salt;

    @CreatedDate
    private LocalDateTime createdAt;

    private String secretKey;

    public Corporation(String corporationId, String name) {
        this.corporationId = corporationId;
        this.name = name;
    }

    public Corporation(String salt, String name, String corporationId) {
        this.salt = salt;
        this.name = name;
        this.corporationId = corporationId;
    }

    public static Corporation create(CorporationCreationCommand corporationCreationCommand, String salt) {
        return new Corporation(UUID.randomUUID().toString().substring(0,6), corporationCreationCommand.getName(), salt);
    }
}
