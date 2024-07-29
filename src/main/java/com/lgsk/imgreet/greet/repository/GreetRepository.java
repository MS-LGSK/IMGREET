package com.lgsk.imgreet.greet.repository;

import com.lgsk.imgreet.entity.Greet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetRepository extends JpaRepository<Greet, Long> {
}
