package com.zkrypto.zkwalletWithCustody.domain.member.presentation;

import com.zkrypto.zkwalletWithCustody.domain.auth.application.service.AuthService;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletResponse;
import com.zkrypto.zkwalletWithCustody.domain.member.application.dto.response.MemberResponse;
import com.zkrypto.zkwalletWithCustody.domain.member.application.service.MemberService;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "MemberController", description = "멤버 API")
public class MemberController {
    private final AuthService authService;
    private final MemberService memberService;

    @Operation(
            summary = "로그아웃 API",
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            },
            parameters = {
                    @Parameter(
                            in = ParameterIn.HEADER,
                            name = "Authorization",
                            description = "Bearer 토큰",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(schema = @Schema(implementation = Void.class))}),
    })
    @DeleteMapping()
    public void signOut(@AuthenticationPrincipal UUID memberId){
        authService.signOut(memberId);
    }

    @Operation(
            summary = "멤버 조회 API",
            description = "나의 정보를 조회합니다.",
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            },
            parameters = {
                    @Parameter(
                            in = ParameterIn.HEADER,
                            name = "Authorization",
                            description = "Bearer 토큰",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(schema = @Schema(implementation = MemberResponse.class))}),
    })
    @GetMapping()
    public ApiResponse<MemberResponse> getMember(@AuthenticationPrincipal UUID memberId) {
        return ApiResponse.success(memberService.getMember(memberId));
    }
}
