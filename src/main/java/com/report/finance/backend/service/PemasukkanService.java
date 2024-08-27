package com.report.finance.backend.service;

import java.math.BigInteger;

import com.report.finance.backend.dto.PemasukkanInputReq;
import com.report.finance.backend.dto.SearchPemasukkanRequest;
import com.report.finance.backend.dto.SearchResponse;
import com.report.finance.backend.entity.PemasukanEntity;

public interface PemasukkanService {
    
    PemasukanEntity catatPemasukkan(PemasukkanInputReq req, String username);
    PemasukanEntity editPemasukan(PemasukkanInputReq req, BigInteger id, String username);
    PemasukanEntity getPemasukan(BigInteger id);
    SearchResponse<PemasukanEntity> searchPemasukan(SearchPemasukkanRequest req);
    void deletePemasukan(BigInteger id, String username);
}
