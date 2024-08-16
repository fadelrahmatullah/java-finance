package com.report.finance.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PengeluaranInputReq {
    
    private String typeEnum;

    private String deskripsi;

    private Double jumlah;

    private java.sql.Date tglPengeluaran;
}
