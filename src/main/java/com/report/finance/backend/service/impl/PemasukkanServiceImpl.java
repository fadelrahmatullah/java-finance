package com.report.finance.backend.service.impl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.report.finance.backend.dto.PemasukkanInputReq;
import com.report.finance.backend.dto.TypePemasukkanEnum;
import com.report.finance.backend.dto.ValidationException;
import com.report.finance.backend.entity.PemasukanEntity;
import com.report.finance.backend.repository.PemasukanRepository;
import com.report.finance.backend.service.PemasukkanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PemasukkanServiceImpl implements PemasukkanService{

    private final PemasukanRepository pemasukanRepository;

    @Override
    @Transactional
    public PemasukanEntity catatPemasukkan(PemasukkanInputReq req, String username) {
        
        PemasukanEntity entity = new PemasukanEntity();

       this.validEnum(req.getTypeEnum());

        BeanUtils.copyProperties(req, entity);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
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
    

}
