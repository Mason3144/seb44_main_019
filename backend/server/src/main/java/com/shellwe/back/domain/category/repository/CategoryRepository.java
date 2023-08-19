package com.shellwe.back.domain.category.repository;

import com.shellwe.back.entity.type.Category;
import com.shellwe.back.entity.ShellCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByShellCategory(ShellCategory shellCategory);
}
