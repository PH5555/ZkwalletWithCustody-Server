package com.zkrypto.zkwalletWithCustody.domain.note.application.service;

import com.zkrypto.zkwalletWithCustody.domain.note.application.dto.event.NoteCreationEventDto;
import com.zkrypto.zkwalletWithCustody.domain.note.application.dto.event.NoteUpdateEventDto;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class NoteEventListener {
    private final NoteService noteService;

    @Async
    @EventListener
    public void process(NoteCreationEventDto event) throws Exception {
        log.info("노트 생성 start");
        // 노트 생성
        Note note = noteService.getNote(event.getCt(), event.getCommitment(), event.getNumLeaves(), event.getCorporation());

        // 노트 생성 확인
        if(!note.isOwner()) {
            throw new IllegalArgumentException("자신의 노트가 아닙니다.");
        }

        // 노트 저장
        noteService.saveNote(note);
    }

    @Async
    @EventListener
    public void process(NoteUpdateEventDto event) throws Exception {
        // 트랜잭션에서 노트가 쓰였으면 업데이트
        log.info("노트 수정 이벤트");
        if(event.getTransaction().getFromUnSpentNote() != null) {
            log.info("노트 수정 시작");
            noteService.updateNoteSpend(event.getTransaction().getFromUnSpentNote().getNoteId(), event.getTransaction().getSender());
        }
    }
}
