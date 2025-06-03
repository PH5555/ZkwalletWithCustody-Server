package com.zkrypto.zkwalletWithCustody.domain.transaction.presentation;

import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.CorporationResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletCreationResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletResponse;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.response.TransactionResponse;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.service.TransactionService;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Type;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Tag(name = "TransactionController", description = "트랜잭션 API")
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(
            summary = "트랜잭션 조회 API",
            description = "ADMIN은 트랜잭션 상태와 상관없이 모든 트랜잭션을 조회합니다. USER는 대기중인 트랜잭션을 요청했을 경우는 자신이 보낸 트랜잭션만 조회 가능하고, 서명 완료된 트랜잭션을 요청했을 경우에는 내가 보낸 트랜잭션과 내가 받은 트랜잭션을 모두 조회 가능합니다.",
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
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class)))}),
    })
    @GetMapping("")
    public ApiResponse<List<TransactionResponse>> getTransactions(@AuthenticationPrincipal UUID memberId, @RequestParam(required = false) Status status, @RequestParam(required = false) Type type) {
        return ApiResponse.success(transactionService.getTransactions(memberId, status, type));
    }

    @Operation(
            summary = "트랜잭션 요청 API",
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            },
            parameters = {
                    @Parameter(
                            in = ParameterIn.HEADER,
                            name = "Authorization",
                            description = "Bearer 토큰 (ROLE_USER 사용자만 접근 가능)",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(schema = @Schema(implementation = Void.class))}),
    })
    @PostMapping("")
    public void createTransaction(@AuthenticationPrincipal UUID memberId, @RequestBody TransactionCreationCommand transactionCreationCommand) {
        transactionService.createTransaction(memberId, transactionCreationCommand);
    }
}
