package com.zkrypto.zkwalletWithCustody.domain.transaction.presentation;

import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.CorporationResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletCreationResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletResponse;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionUpdateCommand;
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
            description = "ADMIN은 트랜잭션 상태와 상관없이 모든 트랜잭션을 조회합니다. USER는 대기중인 트랜잭션을 요청했을 경우는 자신이 보낸 트랜잭션만 조회 가능하고, 서명 완료된 트랜잭션을 요청했을 경우에는 내가 보낸 트랜잭션과 내가 받은 트랜잭션을 모두 조회 가능합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class)))}),
    })
    @GetMapping("")
    public ApiResponse<List<TransactionResponse>> getTransactions(@Parameter(description = "값을 넣을 경우 USER, 넣지 않을 경우 ADMIN", required = false) @RequestParam(required = false) UUID memberId,@Parameter(description = "DONE이면 완료된것만 가져오기(트랜잭션 내역 페이지), NONE이면 완료되지 않은것만 가져오기(트랜잭션 요청 페이지)", required = false) @RequestParam(required = false) Status status, @Parameter(description = "RECEIVE면 받은거 가져오기, SEND면 보낸거 가져오기,", required = false) @RequestParam(required = false) Type type) {
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


    @Operation(
            summary = "트랜잭션 전송 시작 API",
            description = "클라이언트에서 트랜잭션 zktransfer를 실행하기 전에 이 API를 호출하면 됩니다. API 성공시 zktransfer 실행, API 실패시 zktransfer도 미실행"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(schema = @Schema(implementation = Void.class))}),
    })
    @PutMapping("")
    public void signTransaction(@RequestBody TransactionUpdateCommand transactionUpdateCommand) {
        transactionService.monitorTransaction(transactionUpdateCommand);
    }

    @Operation(
            summary = "C레벨 임원측 트랜잭션 서명 API",
            description = "C레벨 임원측에서 트랜잭션에 대한 서명을 하는 API입니다. 모든 C레벨 임원이 서명을 해야 커스터디가 최종으로 서명할 수 있습니다.",
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
    @PatchMapping("")
    public void checkTransaction(@AuthenticationPrincipal UUID memberId, @RequestBody TransactionUpdateCommand transactionUpdateCommand) {
        transactionService.checkTransaction(memberId, transactionUpdateCommand);
    }
}
