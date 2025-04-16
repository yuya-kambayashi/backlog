package com.bluejeanssystems.backlog.repository;

import com.bluejeanssystems.backlog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.project.id = :projectId")
    List<Category> findAllBy(Long projectId);
}
