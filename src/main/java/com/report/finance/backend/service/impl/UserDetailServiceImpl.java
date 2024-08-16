package com.report.finance.backend.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.report.finance.backend.entity.UserInfoEntity;
import com.report.finance.backend.repository.UserInfoRepository;
import java.util.Collection;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{

    private final UserInfoRepository userInfoRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfoEntity user = userInfoRepository.getUserByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        String userName = user.getUsername();
    	String password = user.getPassword();
    	
        if (userName.equals(username)) {
            return new UserDetails() {

                private static final long serialVersionUID = -5226441188073084235L;

                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return null;
                }

                @Override
                public String getPassword() {                	
                	return password;
                }

                @Override
                public String getUsername() {
                    return userName;
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }

            };
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
    
}
