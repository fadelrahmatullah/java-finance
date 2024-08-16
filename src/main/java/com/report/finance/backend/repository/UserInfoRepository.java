package com.report.finance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.report.finance.backend.entity.UserInfoEntity;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long>{
    
    @Query("FROM UserInfoEntity WHERE userName = :userName")
	UserInfoEntity getUserByUserName(@Param("userName") String userName);
}
