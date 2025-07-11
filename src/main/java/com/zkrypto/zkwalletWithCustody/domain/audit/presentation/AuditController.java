package com.zkrypto.zkwalletWithCustody.domain.audit.presentation;

import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.request.AuditCommand;
import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.response.AuditDataResponse;
import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.response.AuditResultResponse;
import com.zkrypto.zkwalletWithCustody.domain.audit.application.service.AuditService;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.response.TransactionResponse;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "AuditController", description = "감사 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/audit")
public class AuditController {
    private final AuditService auditService;

    @Operation(
            summary = "감사 데이터 조회 API"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = AuditDataResponse.class)))}),
    })
    @GetMapping("")
    public ApiResponse<List<AuditDataResponse>> getAuditData() {
        return ApiResponse.success(auditService.getAuditData());
    }

    @Operation(
            summary = "감사 진행 API"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(schema = @Schema(implementation = AuditResultResponse.class))}),
    })
    @PostMapping("")
    public ApiResponse<AuditResultResponse> processAudit(@RequestBody AuditCommand auditCommand) {
        return ApiResponse.success(auditService.processAudit(auditCommand));
    }
}
