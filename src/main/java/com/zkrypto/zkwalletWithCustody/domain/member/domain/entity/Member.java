package com.zkrypto.zkwalletWithCustody.domain.member.domain.entity;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.SignUpCommand;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID memberId;

    private String name;
    private String loginId;
    private String password;
    private String position;
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "corporation_id")
    private Corporation corporation;

    @CreatedDate
    private LocalDateTime createdAt;

    private Member(String name, String loginId, String password, String position, Corporation corporation, Role role) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.position = position;
        this.role = role;
    }

    public static Member join(SignUpCommand signUpCommand, String hashedPassword, Corporation corporation) {
        return new Member(signUpCommand.getName(), signUpCommand.getLoginId(), hashedPassword, signUpCommand.getPosition(), corporation, Role.ROLE_USER);
    }

    public void storeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void signOut() {
        refreshToken = null;
    }
}
