package com.zkrypto.zkwalletWithCustody.domain.auth.presentation;

import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.ReissueCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.SignInCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.request.SignUpCommand;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.dto.response.AuthTokenResponse;
import com.zkrypto.zkwalletWithCustody.domain.auth.application.service.AuthService;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "AuthController", description = "로그인 및 회원가입 API")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(schema = @Schema(implementation = Void.class))}),
    })
    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpCommand signUpCommand) {
        authService.signUp(signUpCommand);
    }

    @Operation(summary = "로그인 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(schema = @Schema(implementation = AuthTokenResponse.class))}),
    })
    @PostMapping("/signin")
    public ApiResponse<AuthTokenResponse> signIn(@RequestBody SignInCommand signInCommand) {
        return ApiResponse.success(authService.signIn(signInCommand));
    }

    @Operation(summary = "토큰 재발급 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(schema = @Schema(implementation = AuthTokenResponse.class))}),
    })
    @PostMapping("/reissue")
    public ApiResponse<AuthTokenResponse> reissue(@RequestBody ReissueCommand reissueCommand) {
        return ApiResponse.success(authService.reissue(reissueCommand));
    }
}
