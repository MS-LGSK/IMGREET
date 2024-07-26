package com.lgsk.imgreet.share.repository;

import com.lgsk.imgreet.entity.Greet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Greet, Long> {
}
