package com.zkrypto.zkwalletWithCustody.web3;

import com.zkrypto.zkwalletWithCustody.global.web3.Groth16AltBN128Mixer;
import com.zkrypto.zkwalletWithCustody.global.web3.Web3Service;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@SpringBootTest
public class Web3Test {
    @Autowired
    private Web3Service web3Service;

    @Value("${contract.mixer.address}")
    private String registerUserContractAddress;

    @Test
    void ENA_가져오기() {
        Groth16AltBN128Mixer smartContract = web3Service.loadContract("77982682394618774300880293173989232703111784738901116327468465577013636406088", registerUserContractAddress);
        CompletableFuture<Tuple3<BigInteger, BigInteger, Groth16AltBN128Mixer.CurvePoint>> tuple3CompletableFuture = smartContract.getUserPublicKeys("0xf10c25ced0a2b0c01bc59df04b9f0ac4a1528e01").sendAsync();

    }
}
