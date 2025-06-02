package com.zkrypto.zkwalletWithCustody.domain.transaction.presentation;

import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.response.TransactionResponse;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.service.TransactionService;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Type;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("")
    public ApiResponse<List<TransactionResponse>> getTransactions(@AuthenticationPrincipal UUID memberId, @RequestParam Status status, @RequestParam Type type) {
        return ApiResponse.success(transactionService.getTransactions(memberId, status, type));
    }

    @PostMapping("")
    public void createTransaction(@AuthenticationPrincipal UUID memberId, @RequestBody TransactionCreationCommand transactionCreationCommand) {
        transactionService.createTransaction(memberId, transactionCreationCommand);
    }
}
