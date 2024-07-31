package com.lgsk.imgreet.component.repository;

import com.lgsk.imgreet.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentRepository extends JpaRepository<Component, Long> {
    List<Component> findAllByGreetId(Long id);
    List<Component> findAllByTemplateId(Long id);
}
