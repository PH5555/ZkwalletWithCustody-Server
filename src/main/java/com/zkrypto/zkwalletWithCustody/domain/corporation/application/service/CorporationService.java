package com.zkrypto.zkwalletWithCustody.domain.corporation.application.service;

import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.WalletCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.CorporationMembersResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.CorporationResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletCreationResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.response.WalletResponse;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.constant.UPK;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.AffinePoint;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.MiMC7;
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.AESUtils;
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.EcUtils;
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.SaltUtils;
import com.zkrypto.zkwalletWithCustody.global.web3.Web3Service;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CorporationService {
    private final CorporationRepository corporationRepository;
    private final AESUtils aesUtils;
    private final Web3Service web3Service;

    @Value("${contract.mixer.address}")
    private String registerUserContractAddress;

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
        BigInteger usk = deriveUskFromPrivateKey(privateKey);

        // usk 저장
        String cipherUsk = aesUtils.encrypt(usk.toString(), corporation.getSalt());
        corporation.setSecretKey(cipherUsk);

        // ena 등록
//        UPK upk = recoverFromUserSk(usk);
//        Groth16AltBN128Mixer smartContract = web3Service.loadContract(privateKey.toString(16), registerUserContractAddress);
//        smartContract.registerUser(Numeric.toBigInt(corporation.getAddress()), upk.getPkOwn(), List.of(upk.getPkEnc().getX(), upk.getPkEnc().getY())).send();
        return new WalletCreationResponse(privateKey.toString(16));
    }

    /**
     * 지갑 반환 메서드
     */
    @Transactional
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

        return WalletResponse.from(corporation.getAddress(), upk, usk);
    }

    /**
     * 모든 멤버 반환 메서드
     */
    @Transactional
    public List<CorporationMembersResponse> getAllMembers(String corporationId) {
        // 법인 존재 확인
        Corporation corporation = corporationRepository.findWithMembersByCorporationId(corporationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 법인입니다."));

        return corporation.getMembers().stream().map(member -> new CorporationMembersResponse(member.getName(), member.getPosition(), member.getCreatedAt(), member.getMemberId().toString())).toList();
    }

    private BigInteger generateWallet(Corporation corporation) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        ECKeyPair keyPair = Keys.createEcKeyPair();
        BigInteger privateKey = keyPair.getPrivateKey();
        String address = "0x" + Keys.getAddress(keyPair);

        corporation.setAddress(address);
        return privateKey;
    }

    private UPK recoverFromUserSk(BigInteger sk) {
        MiMC7 mimc = new MiMC7();
        BigInteger pkOwn = mimc.hash(sk);
        AffinePoint pkEnc = EcUtils.basePointMul(sk);
        BigInteger ena = mimc.hash(pkOwn, pkEnc.getX(), pkEnc.getY());
        return new UPK(ena, pkOwn, pkEnc);
    }

    private BigInteger deriveUskFromPrivateKey(BigInteger privateKey) {
        MiMC7 mimc = new MiMC7();
        return mimc.hash(privateKey);
    }

}
