package com.fujielectric.ficks.domain.history;

import com.fujielectric.ficks.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    public List<History> findByUserAndAction(User user, Action action);
}
