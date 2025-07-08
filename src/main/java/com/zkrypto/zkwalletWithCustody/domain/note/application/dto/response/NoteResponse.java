package com.zkrypto.zkwalletWithCustody.domain.note.application.dto.response;

import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import lombok.Getter;

import java.util.UUID;

@Getter
public class NoteResponse {
    private UUID noteId;
    private String open;
    private String tokenAddress;
    private String tokenId;
    private String amount;
    private String addr;
    private String commitment;
    private String index;
    private boolean isSpent;

    private NoteResponse(UUID noteId, String open, String tokenAddress, String tokenId, String amount, String addr, String commitment, String index, boolean isSpent) {
        this.noteId = noteId;
        this.open = open;
        this.tokenAddress = tokenAddress;
        this.tokenId = tokenId;
        this.amount = amount;
        this.addr = addr;
        this.commitment = commitment;
        this.index = index;
        this.isSpent = isSpent;
    }

    public static NoteResponse from(Note note) {
        return new NoteResponse(
                note.getNoteId(),
                note.getOpen(),
                note.getTokenAddress(),
                note.getTokenId(),
                note.getAmount(),
                note.getAddr(),
                note.getCommitment(),
                note.getIndex(),
                note.getIsSpent());
    }
}
