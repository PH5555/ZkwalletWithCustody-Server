package com.zkrypto.zkwalletWithCustody.domain.Corporation.presentation;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.service.CorporationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public void getAllCorporation() {

    }

    @PostMapping("/wallet")
    public void createCorporationWallet() {

    }

    @GetMapping("/wallet")
    public void getCorporationWallet() {

    }
}
