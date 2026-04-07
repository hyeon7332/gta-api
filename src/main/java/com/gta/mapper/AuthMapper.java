package com.gta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gta.dto.PendingUserResponse;
import com.gta.dto.SignupRequest;
import com.gta.dto.UserApprovalRequest;
import com.gta.entity.User;

/**
 * 인증 관련 Mapper
 */
@Mapper
public interface AuthMapper {
	int insertUser(SignupRequest request);
	
	int countByLoginId(String loginId);
	
	User findByLoginId(String loginId);
	
	int updateUserStatus(UserApprovalRequest request);

	List<PendingUserResponse> selectPendingUsers();
}
