package com.lgsk.imgreet.component.repository;

import com.lgsk.imgreet.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {
}
