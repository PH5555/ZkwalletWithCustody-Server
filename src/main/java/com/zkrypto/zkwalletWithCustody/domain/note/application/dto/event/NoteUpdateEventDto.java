package com.zkrypto.zkwalletWithCustody.domain.note.application.dto.event;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class NoteUpdateEventDto {
    private Transaction transaction;
}
