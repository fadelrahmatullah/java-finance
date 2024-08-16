package com.report.finance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.report.finance.backend.entity.TokenBlacklistEntity;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklistEntity, Long> {
    
}
