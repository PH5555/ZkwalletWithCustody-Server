package com.zkrypto.zkwalletWithCustody.domain.corporation.presentation;

import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.WalletCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.CorporationMembersResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.CorporationResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletCreationResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/corporation")
@RequiredArgsConstructor
@Tag(name = "CorporationController", description = "법인 생성 및 지갑 생성 API")
public class CorporationController {
    private final CorporationService corporationService;

    @Operation(
            summary = "법인 생성 API"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(schema = @Schema(implementation = Void.class))}),
    })
    @PostMapping()
    public void createCorporation(@RequestBody CorporationCreationCommand corporationCreationCommand) {
        corporationService.createCorporation(corporationCreationCommand);
    }

    @Operation(
            summary = "법인 조회 API",
            description = "존재하는 법인을 모두 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CorporationResponse.class)))}),
    })
    @GetMapping()
    public ApiResponse<List<CorporationResponse>> getAllCorporation() {
        return ApiResponse.success(corporationService.getAllCorporation());
    }


    @Operation(
            summary = "월렛 생성 API",
            description = "EOA 월렛과 ENA 월렛을 생성해주는 API 입니다. ENA 월렛은 DB와 블록체인에 저장되고, EOA private key를 응답으로 받아 콜드월렛에 저장합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(schema = @Schema(implementation = WalletCreationResponse.class))}),
    })
    @PostMapping("/wallet")
    public ApiResponse<WalletCreationResponse> createCorporationWallet(@RequestBody WalletCreationCommand walletCreationCommand) throws Exception {
        return ApiResponse.success(corporationService.createCorporationWallet(walletCreationCommand));
    }

    @Operation(
            summary = "월렛 조회 API",
            description = "EOA address와 ENA 월렛 정보를 반환해주는 API 입니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(schema = @Schema(implementation = WalletResponse.class))}),
    })
    @GetMapping("/wallet")
    public ApiResponse<WalletResponse> getCorporationWallet(@RequestParam String corporationId) throws Exception {
        return ApiResponse.success(corporationService.getWallet(corporationId));
    }

    @Operation(
            summary = "법인 멤버 조회 API",
            description = "법인의 모든 멤버를 조회하는 API입니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CorporationMembersResponse.class)))}),
    })
    @GetMapping("/members")
    public ApiResponse<List<CorporationMembersResponse>> getCorporationMembers(@RequestParam String corporationId) {
        return ApiResponse.success(corporationService.getAllMembers(corporationId));
    }
}
