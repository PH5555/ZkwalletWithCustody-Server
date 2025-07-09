package com.zkrypto.zkwalletWithCustody.domain.audit.presentation;

import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.request.AuditCommand;
import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.response.AuditDataResponse;
import com.zkrypto.zkwalletWithCustody.domain.audit.application.dto.response.AuditResultResponse;
import com.zkrypto.zkwalletWithCustody.domain.audit.application.service.AuditService;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/audit")
public class AuditController {
    private final AuditService auditService;

    @GetMapping("")
    public ApiResponse<List<AuditDataResponse>> getAuditData() {
        return ApiResponse.success(auditService.getAuditData());
    }

    @PostMapping("")
    public ApiResponse<AuditResultResponse> processAudit(@RequestBody AuditCommand auditCommand) {
        return ApiResponse.success(auditService.processAudit(auditCommand));
    }
}
