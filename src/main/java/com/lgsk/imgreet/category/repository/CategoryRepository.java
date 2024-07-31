package com.lgsk.imgreet.category.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lgsk.imgreet.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findAllByOrderById();
}
