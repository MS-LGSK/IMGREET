package com.lgsk.imgreet.category;

import com.lgsk.imgreet.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
