package com.lgsk.imgreet.greet.repository;

import com.lgsk.imgreet.entity.Greet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GreetRepository extends JpaRepository<Greet, Long> {
    List<Greet> findByUser_Id(Long userId);
}
