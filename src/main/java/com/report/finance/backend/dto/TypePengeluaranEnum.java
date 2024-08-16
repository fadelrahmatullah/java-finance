package com.report.finance.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypePengeluaranEnum {

    BELANJA_BULANAN("PEN001","Belanja Bulanan"),
    BELANJA_MINGGUAN("PEN002","Belanja Mingguan"),
    E_COMMERCE("PEN003","E_COMMERCE"),
    OTHERS("PEN004","Other");
    
    private final String code;
    private final String value;

    public static TypePemasukkanEnum fromCode(String code) {
        for (TypePemasukkanEnum type : TypePemasukkanEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
