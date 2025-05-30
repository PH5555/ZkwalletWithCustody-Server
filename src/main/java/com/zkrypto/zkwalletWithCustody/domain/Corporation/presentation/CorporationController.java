package com.zkrypto.zkwalletWithCustody.domain.Corporation.presentation;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.response.CorporationResponse;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/corporation")
@RequiredArgsConstructor
public class CorporationController {
    private final CorporationService corporationService;

    @PostMapping()
    public void createCorporation(@RequestBody CorporationCreationCommand corporationCreationCommand) {
        corporationService.createCorporation(corporationCreationCommand);
    }

    @GetMapping()
    public ApiResponse<List<CorporationResponse>> getAllCorporation() {
        return ApiResponse.success(corporationService.getAllCorporation());
    }

    @PostMapping("/wallet")
    public void createCorporationWallet() {

    }

    @GetMapping("/wallet")
    public void getCorporationWallet() {

    }
}
