package com.zkrypto.zkwalletWithCustody.global.web3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;

@Service
@RequiredArgsConstructor
public class Web3Service {
    private final Web3j web3j;
    private final ContractGasProvider contractGasProvider;

    public Groth16AltBN128Mixer loadContract(String privateKey, String contractAddress) {
        Credentials credentials = Credentials.create(privateKey);
        return Groth16AltBN128Mixer.load(
                contractAddress,
                web3j,
                credentials,
                contractGasProvider
        );
    }
}
