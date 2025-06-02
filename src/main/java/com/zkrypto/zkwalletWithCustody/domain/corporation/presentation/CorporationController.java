package com.zkrypto.zkwalletWithCustody.domain.corporation.presentation;

import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.WalletCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.CorporationResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletCreationResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ApiResponse<WalletCreationResponse> createCorporationWallet(@RequestBody WalletCreationCommand walletCreationCommand) throws Exception {
        return ApiResponse.success(corporationService.createCorporationWallet(walletCreationCommand));
    }

    @GetMapping("/wallet")
    public ApiResponse<WalletResponse> getCorporationWallet(@RequestParam String corporationId) throws Exception {
        return ApiResponse.success(corporationService.getWallet(corporationId));
    }
}
