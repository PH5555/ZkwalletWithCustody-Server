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
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.AESUtils;
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.SaltUtils;
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.WalletUtils;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
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
        BigInteger privateKey = WalletUtils.generateWallet(corporation);
        BigInteger usk = WalletUtils.deriveUskFromPrivateKey(privateKey);

        // usk 저장
        String cipherUsk = AESUtils.encrypt(usk.toString(), corporation.getSalt());
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
    public WalletResponse getWallet(String corporationId, String address) throws Exception {
        // 법인 존재 확인
        Corporation corporation = null;
        if(corporationId != null) {
            corporation = corporationRepository.findCorporationByCorporationId(corporationId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 법인입니다."));
        }
        else if(address != null) {
            corporation = corporationRepository.findCorporationByAddress(address)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 법인입니다."));
        }
        else {
            throw new IllegalArgumentException("corporationId나 address 값이 없습니다.");
        }

        // 지갑 있는지 확인
        if(StringUtils.isEmpty(corporation.getAddress())) {
            throw new IllegalArgumentException("지갑이 존재하지 않습니다.");
        }

        // upk 생성
        String usk = AESUtils.decrypt(corporation.getSecretKey(), corporation.getSalt());
        UPK upk = WalletUtils.recoverFromUserSk(new BigInteger(usk));

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
}
