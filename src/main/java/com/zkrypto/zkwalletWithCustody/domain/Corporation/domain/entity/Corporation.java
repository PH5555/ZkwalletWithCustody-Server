package com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.entity;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Corporation {
    @Id
    private String corporationId;

    private String name;
    private String address;
    private String salt;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "corporation")
    private List<Member> members = new ArrayList<>();

    private String secretKey;

    public Corporation(String name, String address) {
        this.corporationId = UUID.randomUUID().toString().substring(0,6);
        this.name = name;
        this.address = address;
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
