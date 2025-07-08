package com.zkrypto.zkwalletWithCustody.domain.note.presentation;

import com.zkrypto.zkwalletWithCustody.domain.note.application.dto.response.NoteResponse;
import com.zkrypto.zkwalletWithCustody.domain.note.application.service.NoteService;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/")
    public ApiResponse<List<NoteResponse>> getNotes(@RequestParam String corporationId) {
        return ApiResponse.success(noteService.getCorporationNotes(corporationId));
    }
}
