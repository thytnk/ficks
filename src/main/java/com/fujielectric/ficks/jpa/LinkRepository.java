package com.fujielectric.ficks.jpa;

import com.fujielectric.ficks.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Integer> {
}
