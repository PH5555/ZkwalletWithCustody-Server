package com.zkrypto.zkwalletWithCustody.domain.transaction.presentation;

import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("")
    public void getTransactions() {

    }

    @PostMapping("")
    public void createTransaction(@AuthenticationPrincipal UUID memberId, @RequestBody TransactionCreationCommand transactionCreationCommand) {
        transactionService.createTransaction(memberId, transactionCreationCommand);
    }
}
