package com.lgsk.imgreet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "BOOKMARKS")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark implements Serializable {

    @EmbeddedId
    private BookmarkId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @MapsId("templateId")
    @JoinColumn(name = "TEMPLATE_ID")
    private Template template;

    public Long getUserId() {
        return id.getUserId();
    }

    public Long getTemplateId() {
        return id.getTemplateId();
    }
}
