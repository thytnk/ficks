package com.fujielectric.ficks.jpa;

import com.fujielectric.ficks.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
