package com.zkrypto.zkwalletWithCustody.domain.transaction.application.service;

import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.event.NoteEventDto;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Note;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NoteEventListener {
    private final NoteService noteService;

    @EventListener
    public void process(NoteEventDto noteEventDto) throws Exception {
        Note note = noteService.getNote(noteEventDto.getCt(), noteEventDto.getCommitment(), noteEventDto.getCorporation());
        if(!noteService.isOwner(note)) {
            throw new IllegalArgumentException("자신의 노트가 아닙니다.");
        }


    }
}
