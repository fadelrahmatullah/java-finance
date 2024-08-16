package com.report.finance.backend.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_pemasukan")
public class PemasukanEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jumlah", precision = 15, scale = 2, columnDefinition = "DECIMAL(15, 2) DEFAULT 0.00")
    private Double jumlah;

    @Column(name = "type_enum", nullable = false, length = 10)
    private String typeEnum;

    @Column(name = "deskripsi", length = 255)
    private String deskripsi;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "tgl_pemasukkan", nullable = false)
    private java.sql.Date tglPemasukan;

}
