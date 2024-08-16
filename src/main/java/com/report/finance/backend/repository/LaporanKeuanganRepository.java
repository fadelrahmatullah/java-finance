package com.report.finance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.report.finance.backend.entity.LaporanKeuanganEntity;

public interface LaporanKeuanganRepository extends JpaRepository<LaporanKeuanganEntity, Long>{
    
}
