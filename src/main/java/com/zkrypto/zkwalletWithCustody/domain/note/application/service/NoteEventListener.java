package com.zkrypto.zkwalletWithCustody.domain.note.application.service;

import com.zkrypto.zkwalletWithCustody.domain.note.application.dto.event.NoteEventDto;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NoteEventListener {
    private final NoteService noteService;

    @Async
    @EventListener
    public void process(NoteEventDto noteEventDto) throws Exception {
        // 노트 생성
        Note note = noteService.getNote(noteEventDto.getCt(), noteEventDto.getCommitment(), noteEventDto.getNumLeaves(), noteEventDto.getCorporation());

        // 노트 생성 확인
        if(!noteService.isOwner(note)) {
            throw new IllegalArgumentException("자신의 노트가 아닙니다.");
        }

        // 노트 저장
        noteService.saveNote(note);
    }
}
