package com.zkrypto.zkwalletWithCustody.domain.note.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    @Query("select note from Note note where note.corporation.corporationId = :corporationId and note.isSpent = false")
    List<Note> findNotSpendNotesByCorporation(@Param("corporationId") String corporationId);

    Optional<Note> findNoteByNoteId(UUID noteId);
}
