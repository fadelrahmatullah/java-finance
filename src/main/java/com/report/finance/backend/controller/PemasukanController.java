package com.report.finance.backend.controller;

import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.report.finance.backend.dto.PemasukkanInputReq;
import com.report.finance.backend.dto.Response;
import com.report.finance.backend.dto.SearchPemasukkanRequest;
import com.report.finance.backend.dto.SearchResponse;
import com.report.finance.backend.entity.PemasukanEntity;
import com.report.finance.backend.service.PemasukkanService;
import com.report.finance.backend.util.ResponseUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PemasukanController extends BaseController{
    
	private final ResponseUtil responseUtil;
    private final PemasukkanService pemasukkanService;

    @PostMapping("addPemasukkan")
	public Response<PemasukanEntity> adduser(@RequestBody @Valid PemasukkanInputReq req, Authentication authentication) {

		PemasukanEntity userInfo = pemasukkanService.catatPemasukkan(req, this.getLoginUsername(authentication));

		return responseUtil.generateResponseSuccess(userInfo);
	}

    @PostMapping("editPemasukkan/{id}")
	public Response<PemasukanEntity> edituser(@RequestBody @Valid PemasukkanInputReq req,@PathVariable BigInteger id, Authentication authentication) {

		PemasukanEntity userInfo = pemasukkanService.editPemasukan(req, id, this.getLoginUsername(authentication));

		return responseUtil.generateResponseSuccess(userInfo);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("search-pemasukkan")
	public Response<SearchResponse> search(Authentication authentication, @Valid SearchPemasukkanRequest request) {
		log.debug("Search Message with Search Params [{}]", request);

		SearchResponse response = pemasukkanService.searchPemasukan(request);

		return responseUtil.generateResponseSuccess(response);
	}
}
