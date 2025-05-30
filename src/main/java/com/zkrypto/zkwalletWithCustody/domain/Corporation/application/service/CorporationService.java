package com.zkrypto.zkwalletWithCustody.domain.Corporation.application.service;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.request.WalletCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.response.CorporationResponse;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.response.WalletResponse;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.repository.CorporationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CorporationService {
    private final CorporationRepository corporationRepository;

    /**
     * 법인 생성 메서드
     */
    @Transactional
    public Corporation createCorporation(CorporationCreationCommand corporationCreationCommand) {
        // 법인 이름 확인
        if(!corporationRepository.existsCorporationByName(corporationCreationCommand.getName())) {
            throw new IllegalArgumentException("이미 존재하는 법인입니다.");
        }

        // salt 생성
        String salt = generateSalt();

        // 법인 생성
        Corporation corporation = Corporation.create(corporationCreationCommand, salt);
        corporationRepository.save(corporation);
        return corporation;
    }

    /**
     * 법인 가져오기 메서드
     */
    @Transactional
    public List<CorporationResponse> getAllCorporation() {
        List<Corporation> corporations = corporationRepository.findAllWithMembers();
        return corporations.stream().map(CorporationResponse::from).toList();
    }

    /**
     * 지갑 생성 메서드
     */
    public WalletResponse createCorporationWallet(WalletCreationCommand walletCreationCommand) {
        // 법인 존재 확인
        Corporation corporation = corporationRepository.findCorporationByCorporationId(walletCreationCommand.getCorporationId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 법인입니다."));

        // 지갑 생성
        String privateKey = generateWallet(corporation);


    }

    public String generateWallet(Corporation corporation) {
        ECKeyPair keyPair = null;
        try {
            keyPair = Keys.createEcKeyPair();
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
        String privateKeyHex = keyPair.getPrivateKey().toString(16);
        String publicKeyHex = keyPair.getPublicKey().toString(16);
        String address = "0x" + Keys.getAddress(keyPair);

        corporation.setAddress(address);
        return privateKeyHex;
    }

    private String generateSalt() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstanceStrong();
            byte[] saltBytes = new byte[16];
            sr.nextBytes(saltBytes);
            return Base64.getEncoder().encodeToString(saltBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
