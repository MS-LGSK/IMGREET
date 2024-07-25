package com.lgsk.imgreet.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKMARKS")
@Builder(toBuilder = true)
public class Bookmark implements Serializable {

    @EmbeddedId
    @Getter(AccessLevel.NONE)
    private BookmarkId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @MapsId("templateId")
    @JoinColumn(name = "TEMPLATE_ID")
    private Template template;
}
