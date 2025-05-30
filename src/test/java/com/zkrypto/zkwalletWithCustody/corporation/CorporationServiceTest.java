package com.zkrypto.zkwalletWithCustody.corporation;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.repository.CorporationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class CorporationServiceTest {

    @Autowired
    private CorporationService corporationService;

    @Autowired
    private CorporationRepository corporationRepository;

    @Test
    void 법인생성() {
        Corporation corporation = corporationService.createCorporation(new CorporationCreationCommand("지크립토"));
        Optional<Corporation> savedCorporation = corporationRepository.findCorporationByCorporationId(corporation.getCorporationId());

        Assertions.assertThat(corporation.getName()).isEqualTo(savedCorporation.get().getName());
    }
}
