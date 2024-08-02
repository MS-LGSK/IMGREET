package com.lgsk.imgreet.greet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgsk.imgreet.entity.Greet;

public interface GreetRepository extends JpaRepository<Greet, Long> {
    List<Greet> findByUser_Id(Long userId);
}
