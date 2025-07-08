package com.zkrypto.zkwalletWithCustody.domain.note.application.dto.event;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;
import java.util.List;

@AllArgsConstructor
@Getter
public class NoteEventDto {
    private List<BigInteger> ct;
    private BigInteger commitment;
    private Corporation corporation;
    private BigInteger numLeaves;
}
