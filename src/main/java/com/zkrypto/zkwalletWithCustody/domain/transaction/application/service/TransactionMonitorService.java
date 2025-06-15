package com.zkrypto.zkwalletWithCustody.domain.transaction.application.service;

import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionUpdateCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository.TransactionRepository;
import com.zkrypto.zkwalletWithCustody.global.web3.Groth16AltBN128Mixer;
import com.zkrypto.zkwalletWithCustody.global.web3.Web3Service;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionMonitorService {
    private final Web3Service web3Service;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    @Value("${contract.mixer.address}")
    private String registerUserContractAddress;

    @Value("${ethereum.privateKey}")
    private String privateKey;

    /***
     *  트랜잭션 조회 이벤트 리스너
     */
    public void monitorTransactionEvent(Long transactionId) {
        // 블럭넘버 조회
        BigInteger blockNumber = transactionRepository.findMaxBlockNumber();

        Groth16AltBN128Mixer smartContract = web3Service.loadContract(privateKey, registerUserContractAddress);
        Flowable<Long> timeoutFlowable = Flowable.timer(1, TimeUnit.MINUTES);
        Flowable<Groth16AltBN128Mixer.LogZkTransferEventResponse> eventFlowable = smartContract.logZkTransferEventFlowable(DefaultBlockParameter.valueOf(blockNumber), DefaultBlockParameterName.LATEST);

        // 블록 조회 이벤트 생성
        AtomicReference<Disposable> subscriptionRef = new AtomicReference<>();
        Disposable subscription = eventFlowable
                .takeUntil(timeoutFlowable)
                .subscribe(event -> {
                    if (valid(event)) {
                        // 트랜잭션 업데이트
                        transactionService.updateTransaction(new TransactionUpdateCommand(transactionId));
                        subscriptionRef.get().dispose();
                    }
                });
        subscriptionRef.set(subscription);
    }

    /***
     *  블럭 유효성 검사
     */
    private Boolean valid(Groth16AltBN128Mixer.LogZkTransferEventResponse event) {
        // TODO: 유효성 검사
        return true;
    }
}
