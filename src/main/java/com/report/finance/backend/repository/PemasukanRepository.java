package com.report.finance.backend.repository;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.report.finance.backend.entity.PemasukanEntity;

public interface PemasukanRepository extends JpaRepository<PemasukanEntity, Long>{
    
    @Query("FROM PemasukanEntity p " +
       "WHERE (:dateFrom IS NULL OR p.tglPemasukan >= :dateFrom) " +
       "AND (:dateTo IS NULL OR p.tglPemasukan <= :dateTo) " +
       "AND (:id IS NULL OR p.id = :id) " +
       "and (:typePemasukkan IS NULL or lower(p.typeEnum) like lower(concat('%', :typePemasukkan, '%'))) " +
       "and (:description IS NULL or lower(p.deskripsi) like lower(concat('%', :description, '%'))) " +
       " ORDER BY p.tglPemasukan DESC "
    )
    Page<PemasukanEntity> search(
                        @Param("dateFrom") Date dateFrom,
                        @Param("dateTo") Date dateTo,
                        @Param("id") Long id,
                        @Param("typePemasukkan") String typePemasukkan,
                        @Param("description") String description,
                        Pageable pageable);

}
