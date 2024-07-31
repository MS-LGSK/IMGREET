package com.lgsk.imgreet.entity;

import com.lgsk.imgreet.base.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TEMPLATES")
@SuperBuilder(toBuilder = true)
public class Template extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long creatorId;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks;
}
