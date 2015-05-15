package com.fujielectric.ficks.jpa;

import com.fujielectric.ficks.domain.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReasonRepository extends JpaRepository<Reason, Integer> {
}
