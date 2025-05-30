package com.zkrypto.zkwalletWithCustody.domain.auth.presentation;

import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.ReissueCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.SignInCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.SignUpCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.response.AuthTokenResponse;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.service.AuthService;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpCommand signUpCommand) {
        authService.signUp(signUpCommand);
    }

    @PostMapping("/signin")
    public ApiResponse<AuthTokenResponse> signIn(@RequestBody SignInCommand signInCommand) {
        return ApiResponse.success(authService.signIn(signInCommand));
    }

    @PostMapping("/reissue")
    public ApiResponse<AuthTokenResponse> reissue(@RequestBody ReissueCommand reissueCommand) {
        return ApiResponse.success(authService.reissue(reissueCommand));
    }
}
