package com.report.finance.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypePemasukkanEnum {
    
    GAJI_BULANAN("PEM001", "Gaji Bulanan"),
    FREELANCE("PEM002", "Freelance"),
    JUALAN("PEM003", "Jualan Barang"),
    BONUS_TAHUNAN("PEM004", "Bonus Tahunan"),
    THR_TAHUNAN("PEM005", "THR Tahunan"),
    OTHERS("PEM006", "Other");

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

    public static boolean isValidCode(String code) {
        for (TypePemasukkanEnum type : TypePemasukkanEnum.values()) {
            if (type.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
