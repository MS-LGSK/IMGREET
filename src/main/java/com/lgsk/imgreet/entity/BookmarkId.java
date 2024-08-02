package com.lgsk.imgreet.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class BookmarkId implements Serializable {

    private Long userId;
    private Long templateId;

//    public BookmarkId(Long userId, Long templateId) {
//        this.userId = userId;
//        this.templateId = templateId;
//    }
}
