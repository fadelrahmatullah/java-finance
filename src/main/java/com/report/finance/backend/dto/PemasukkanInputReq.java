package com.report.finance.backend.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PemasukkanInputReq {
    
    @NotBlank(message = "Type Cannot Be Null")
    private String typeEnum;

    private String deskripsi;

    private Double jumlah;

    private java.sql.Date tglPemasukan;

}
