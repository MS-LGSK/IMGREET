package com.lgsk.imgreet.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgsk.imgreet.entity.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
