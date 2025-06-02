package com.zkrypto.zkwalletWithCustody.corporation;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.request.WalletCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.response.CorporationResponse;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.response.WalletCreationResponse;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.global.crypto.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class CorporationServiceTest {

    @Autowired
    private CorporationService corporationService;

    @Autowired
    private CorporationRepository corporationRepository;

    @Autowired
    private AESUtils aesUtils;

    @Test
    void 법인생성() {
        Corporation corporation = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
        Optional<Corporation> savedCorporation = corporationRepository.findCorporationByCorporationId(corporation.getCorporationId());

        Assertions.assertThat(corporation.getName()).isEqualTo(savedCorporation.get().getName());
    }

    @Test
    void 법인생성_실패() {
        Corporation corporation = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
        Assertions.assertThatThrownBy(() -> {
            corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
        }).hasMessageContaining("이미 존재하는 법인입니다.");
    }

    @Test
    void 법인가져오기() {
        corporationService.createCorporation(new CorporationCreationCommand("지크립토1"));
        corporationService.createCorporation(new CorporationCreationCommand("지크립토2"));
        corporationService.createCorporation(new CorporationCreationCommand("지크립토3"));
        corporationService.createCorporation(new CorporationCreationCommand("지크립토4"));
        List<CorporationResponse> allCorporation = corporationService.getAllCorporation();
        Assertions.assertThat(allCorporation.size()).isEqualTo(4);
    }

    @Test
    void 지갑생성_실패() throws Exception {
        Corporation corporation = new Corporation("지크립토", "test");
        corporationRepository.save(corporation);
        Assertions.assertThatThrownBy(() -> {
            corporationService.createCorporationWallet(new WalletCreationCommand(corporation.getCorporationId()));
        }).hasMessageContaining("이미 지갑이 존재합니다.");
    }

    @Test
    void 지갑생성_성공() throws Exception {
        Corporation corporation = corporationService.createCorporation(new CorporationCreationCommand("지크립토1"));
        corporationService.createCorporationWallet(new WalletCreationCommand(corporation.getCorporationId()));

        Corporation findCorporation = corporationRepository.findCorporationByCorporationId(corporation.getCorporationId()).get();
        Assertions.assertThat(findCorporation.getAddress()).isNotNull();
    }

    @Test
    void 암호화테스트() throws Exception {
        BigInteger test = new BigInteger("1000");
        String cipher = aesUtils.encrypt(test.toString(), "test");
        log.info("cipher : {}", cipher);
        String plain = aesUtils.decrypt(cipher, "test");
        Assertions.assertThat(test).isEqualTo(new BigInteger(plain));
    }
}
