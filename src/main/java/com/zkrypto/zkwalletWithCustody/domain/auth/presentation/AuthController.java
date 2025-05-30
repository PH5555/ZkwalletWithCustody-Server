package com.zkrypto.zkwalletWithCustody.domain.auth.presentation;

import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.SignInCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.SignUpCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void signIn(@RequestBody SignInCommand signInCommand) {
        authService.signIn(signInCommand);
    }
}
