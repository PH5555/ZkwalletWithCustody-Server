package com.zkrypto.zkwalletWithCustody.domain.member.presentation;

import com.zkrypto.zkwalletWithCustody.domain.auth.application.service.AuthService;
import com.zkrypto.zkwalletWithCustody.domain.member.application.dto.response.MemberResponse;
import com.zkrypto.zkwalletWithCustody.domain.member.application.service.MemberService;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final AuthService authService;
    private final MemberService memberService;

    @DeleteMapping()
    public void signOut(@AuthenticationPrincipal UUID memberId){
        authService.signOut(memberId);
    }

    @GetMapping()
    public ApiResponse<MemberResponse> getMember(@AuthenticationPrincipal UUID memberId) {
        return ApiResponse.success(memberService.getMember(memberId));
    }
}
