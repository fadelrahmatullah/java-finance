package com.report.finance.backend.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @NotNull(message = "Jumlah Pemasukkan Cannot Be Null")
    private Double jumlah;

    @NotBlank(message = "tgl Pemasukkan Cannot Be Null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private String tglPemasukan;

}
