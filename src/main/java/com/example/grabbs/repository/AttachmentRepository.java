package com.example.grabbs.repository;

import com.example.grabbs.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findAllByKindId(Long kindId);

    Attachment findByCloudinaryPublicId(String cloudinaryPublicId);
}
