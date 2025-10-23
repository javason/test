package com.admin.tool.repository;

import com.admin.tool.entity.LottoHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LottoHistoryRepository extends JpaRepository<LottoHistory, Long> {

    Page<LottoHistory> findByUsername(String username, Pageable pageable);

    List<LottoHistory> findTop10ByUsernameOrderByGeneratedAtDesc(String username);
}
