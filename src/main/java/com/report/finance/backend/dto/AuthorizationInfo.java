package com.report.finance.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class AuthorizationInfo {

    @NonNull
    private List<String> roleCds;

    @NonNull
    private Boolean isSuperAdmin;
    
}
