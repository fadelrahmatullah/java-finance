package com.report.finance.backend.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_lp_keuangan")
public class LaporanKeuanganEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, length = 75)
    private String username;

    @Column(name = "type_enum", nullable = false, length = 10)
    private String typeEnum;

    @Column(name = "periode", nullable = false)
    private Integer periode;

    @Column(name = "bulan")
    private Integer bulan;

    @Column(name = "tahun")
    private Integer tahun;

    @Column(name = "total_pemasukan", precision = 15, scale = 2, columnDefinition = "DECIMAL(15, 2) DEFAULT 0.00")
    private Double totalPemasukan;

    @Column(name = "total_pengeluaran", precision = 15, scale = 2, columnDefinition = "DECIMAL(15, 2) DEFAULT 0.00")
    private Double totalPengeluaran;

    @Column(name = "saldo", precision = 15, scale = 2, columnDefinition = "DECIMAL(15, 2) DEFAULT 0.00")
    private Double saldo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private UserInfoEntity userInfo;
    
}
