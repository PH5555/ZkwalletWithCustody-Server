package com.zkrypto.zkwalletWithCustody.domain.note.presentation;

import com.zkrypto.zkwalletWithCustody.domain.note.application.dto.response.NoteResponse;
import com.zkrypto.zkwalletWithCustody.domain.note.application.service.NoteService;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.response.TransactionResponse;
import com.zkrypto.zkwalletWithCustody.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/note")
@Tag(name = "NoteController", description = "노트 API")
public class NoteController {
    private final NoteService noteService;

    @Operation(
            summary = "노트 조회 API",
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            },
            parameters = {
                    @Parameter(
                            in = ParameterIn.HEADER,
                            name = "Authorization",
                            description = "Bearer 토큰 (ROLE_USER 사용자만 접근 가능)",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = NoteResponse.class)))}),
    })
    @GetMapping("")
    public ApiResponse<List<NoteResponse>> getNotes(@AuthenticationPrincipal UUID memberId) {
        return ApiResponse.success(noteService.getCorporationNotes(memberId));
    }
}
