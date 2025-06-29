package com.zkrypto.zkwalletWithCustody.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.util.concurrent.Executors;

@Configuration
public class Web3jConfig {

    @Value("${ethereum.rpc.url}")
    private String ethereumRpcUrl;

    @Bean
    public ContractGasProvider contractGasProvider() {
        return new DefaultGasProvider();
    }

    @Bean
    public Web3j web3j() {
        long pollingInterval = 2000L;
        var executor = Executors.newScheduledThreadPool(1);
        return Web3j.build(new HttpService(ethereumRpcUrl), pollingInterval, executor);
    }
}