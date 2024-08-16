package com.report.finance.backend.service;

import java.math.BigInteger;

import com.report.finance.backend.dto.PengeluaranInputReq;
import com.report.finance.backend.entity.PengeluaranEntity;

public interface PengeluaranService {
    
    PengeluaranEntity catatPengeluaran(PengeluaranInputReq req, String username);
    PengeluaranEntity editPengeluaran(PengeluaranInputReq req, BigInteger id, String username);
    PengeluaranEntity getPengeluaran(BigInteger id);
    PengeluaranEntity deletePengeluaran(BigInteger id, String username);
}
