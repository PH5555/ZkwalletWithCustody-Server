package com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
}
