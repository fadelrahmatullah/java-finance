package com.report.finance.backend.service.impl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.report.finance.backend.dto.BaseSearchRequest;
import com.report.finance.backend.dto.PemasukkanInputReq;
import com.report.finance.backend.dto.SearchPemasukkanRequest;
import com.report.finance.backend.dto.SearchResponse;
import com.report.finance.backend.dto.TypePemasukkanEnum;
import com.report.finance.backend.dto.ValidationException;
import com.report.finance.backend.entity.PemasukanEntity;
import com.report.finance.backend.repository.PemasukanRepository;
import com.report.finance.backend.service.PemasukkanService;
import java.text.SimpleDateFormat;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PemasukkanServiceImpl implements PemasukkanService{

    private final PemasukanRepository pemasukanRepository;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    @Transactional
    public PemasukanEntity catatPemasukkan(PemasukkanInputReq req, String username) {
        
        PemasukanEntity entity = new PemasukanEntity();

       this.validEnum(req.getTypeEnum());

        BeanUtils.copyProperties(req, entity);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());

        Date pemasukkan = null;
        try {
                
            pemasukkan = formatter.parse(req.getTglPemasukan()); 

        } catch (Exception e) {
            throw new ValidationException("COMMNERR0001", req.getTglPemasukan()+" Exception date from format dd-MM-yyyy");
        }
        entity.setTglPemasukan(new java.sql.Date(pemasukkan.getTime()));
        pemasukanRepository.save(entity);
        return entity;
    }

    @Override
    @Transactional
    public PemasukanEntity editPemasukan(PemasukkanInputReq req, BigInteger id, String username) {
        
        Optional<PemasukanEntity>  entity = pemasukanRepository.findById(id.longValue());

        if (!entity.isPresent()) {
            throw new ValidationException("ERROR002", id+" Invalid ID ");
        }
        
        this.validEnum(req.getTypeEnum());

        PemasukanEntity pemasukanEntity = entity.get();
        BeanUtils.copyProperties(req, pemasukanEntity);
        pemasukanEntity.setUpdatedAt(new Date());
        pemasukanRepository.save(pemasukanEntity);
        return pemasukanEntity;
    }

    @Override
    public PemasukanEntity getPemasukan(BigInteger id) {

        Optional<PemasukanEntity>  entity = pemasukanRepository.findById(id.longValue());

        if (!entity.isPresent()) {
            throw new ValidationException("ERROR002", id+" Not Found ");
        }

        return entity.get();
    }

    @Override
    @Transactional
    @Modifying
    public void deletePemasukan(BigInteger id, String username) {

        Optional<PemasukanEntity>  entity = pemasukanRepository.findById(id.longValue());

        if (!entity.isPresent()) {
            throw new ValidationException("ERROR002", id+" Not Found ");
        }

        pemasukanRepository.delete(entity.get());
    }

    private void validEnum(String enums){
        if (!TypePemasukkanEnum.isValidCode(enums)) {
            String result = Arrays.stream(TypePemasukkanEnum.values())
                              .map(TypePemasukkanEnum::getCode)
                              .collect(Collectors.joining(", "));
            throw new ValidationException("ERROR002", enums+" Invalid Enum Type, etc Enum : "+result);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public SearchResponse<PemasukanEntity> searchPemasukan(SearchPemasukkanRequest req) {
        
        java.sql.Date dateFrom = null;
        java.sql.Date dateTo = null;
        
        if (StringUtils.isNotBlank(req.getDateFrom())) {

            try {
                Date parsedDateFrom = formatter.parse(req.getDateFrom());
                dateFrom = new java.sql.Date(parsedDateFrom.getTime());
            } catch (Exception e) {
                throw new ValidationException("COMMNERR0001", req.getDateFrom()+" Exception date from format dd-MM-yyyy");
            }
            
        }

        if (StringUtils.isNotBlank(req.getDateTo())) {

            try {

                Date parsedDateTo = formatter.parse(req.getDateTo()); 
                dateTo = new java.sql.Date(parsedDateTo.getTime());
            } catch (Exception e) {
                throw new ValidationException("COMMNERR0001", req.getDateTo()+" Exception date to format dd-MM-yyyy");
            }
            
        }
       
        Page<PemasukanEntity> pageResult = pemasukanRepository.search(
                                dateFrom, 
                                dateTo, 
                                req.getId(), 
                                this.convertValueForLike(req.getTypePemasukkan()),
                                this.convertValueForLike(req.getDescription()),
                                this.getPageable(req));
        
        return this.createSearchResponse(pageResult, req);
    }
    
    private Pageable getPageable(BaseSearchRequest request) {
        return PageRequest.of(request.getPageNo() - 1, request.getPageSize());
    }

    private String convertValueForLike(String value) {
        
        return value != null || !StringUtils.isBlank(value) ? value : null;
    }

    
    @SuppressWarnings("rawtypes")
    private <T> SearchResponse createSearchResponse(Page<T> page, BaseSearchRequest request) {
        List<T> data = page.toList();

        return new SearchResponse<>(request.getPageNo(), request.getPageSize(), data.size(), page.getTotalElements(),
                page.getTotalPages(), data);
    }

}
