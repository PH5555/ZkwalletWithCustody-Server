package com.zkrypto.zkwalletWithCustody.test;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestDataHelper implements ApplicationRunner {
    private final CorporationRepository corporationRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
//        Corporation corporation = new Corporation("1234", "지크립토");
//        corporationRepository.save(corporation);
    }
}
