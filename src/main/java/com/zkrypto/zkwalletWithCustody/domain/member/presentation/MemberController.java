package com.zkrypto.zkwalletWithCustody.domain.member.presentation;

import com.zkrypto.zkwalletWithCustody.domain.auth.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final AuthService authService;

    @DeleteMapping()
    public void signOut(@AuthenticationPrincipal UUID memberId){
        authService.signOut(memberId);
    }
}
