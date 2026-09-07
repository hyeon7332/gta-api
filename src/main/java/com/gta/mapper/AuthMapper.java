package com.gta.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gta.entity.User;

/**
 * 인증 관련 Mapper
 */
@Mapper
public interface AuthMapper {
	
	User findByLoginId(String loginId);
}
