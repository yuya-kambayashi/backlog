package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
