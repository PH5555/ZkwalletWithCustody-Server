package com.zkrypto.zkwalletWithCustody.domain.Corporation.application.service;

import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.request.WalletCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.response.CorporationResponse;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.response.WalletCreationResponse;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.application.dto.response.WalletResponse;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.constant.UPK;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.global.crypto.AESUtils;
import com.zkrypto.zkwalletWithCustody.global.crypto.EcUtils;
import com.zkrypto.zkwalletWithCustody.global.crypto.Mimc7Utils;
import com.zkrypto.zkwalletWithCustody.global.crypto.SaltUtils;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.ec.ECPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.math.BigInteger;
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
    private final Mimc7Utils mimc7Utils;
    private final AESUtils aesUtils;

    /**
     * 법인 생성 메서드
     */
    @Transactional
    public Corporation createCorporation(CorporationCreationCommand corporationCreationCommand) {
        // 법인 이름 확인
        if(corporationRepository.existsCorporationByName(corporationCreationCommand.getName())) {
            throw new IllegalArgumentException("이미 존재하는 법인입니다.");
        }

        // salt 생성
        String salt = SaltUtils.generateSalt();

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
    @Transactional
    public WalletCreationResponse createCorporationWallet(WalletCreationCommand walletCreationCommand) throws Exception {
        // 법인 존재 확인
        Corporation corporation = corporationRepository.findCorporationByCorporationId(walletCreationCommand.getCorporationId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 법인입니다."));

        // 지갑 이미 있는지 확인
        if(!StringUtils.isEmpty(corporation.getAddress())) {
            throw new IllegalArgumentException("이미 지갑이 존재합니다.");
        }

        // 지갑 생성
        BigInteger privateKey = generateWallet(corporation);
        BigInteger usk = mimc7Utils.hash(privateKey);

        // usk 저장
        String cipherUsk = aesUtils.encrypt(usk.toString(), corporation.getSalt());
        corporation.setSecretKey(cipherUsk);

        // ena 등록
        UPK upk = recoverFromUserSk(usk);

        // registerENA 스마트컨트랙트 호출

        return new WalletCreationResponse(privateKey.toString());
    }

    /**
     * 지갑 반환 메서드
     */
    public WalletResponse getWallet(String corporationId) throws Exception {
        // 법인 존재 확인
        Corporation corporation = corporationRepository.findCorporationByCorporationId(corporationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 법인입니다."));

        // 지갑 있는지 확인
        if(StringUtils.isEmpty(corporation.getAddress())) {
            throw new IllegalArgumentException("지갑이 존재하지 않습니다.");
        }

        // upk 생성
        String usk = aesUtils.decrypt(corporation.getSecretKey(), corporation.getSalt());
        UPK upk = recoverFromUserSk(new BigInteger(usk));

        return WalletResponse.from(corporation.getAddress(), upk);
    }

    private BigInteger generateWallet(Corporation corporation) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        ECKeyPair keyPair = Keys.createEcKeyPair();
        BigInteger privateKeyHex = keyPair.getPrivateKey();
        String address = "0x" + Keys.getAddress(keyPair);

        corporation.setAddress(address);
        return privateKeyHex;
    }

    private UPK recoverFromUserSk(BigInteger sk) {
        BigInteger pkOwn = mimc7Utils.hash(sk);
        ECPoint pkEnc = EcUtils.basePointMul(sk);
        BigInteger ena = mimc7Utils.hash(List.of(pkOwn, pkEnc.getAffineXCoord().toBigInteger(), pkEnc.getAffineYCoord().toBigInteger()));
        return new UPK(ena, pkOwn, pkEnc);
    }
}
