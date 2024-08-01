package com.lgsk.imgreet.bookmark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgsk.imgreet.entity.Bookmark;
import com.lgsk.imgreet.entity.BookmarkId;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {

	List<Bookmark> findAllByTemplateId(Long templateId);

	List<Bookmark> findAllByUserId(Long userId);
}
