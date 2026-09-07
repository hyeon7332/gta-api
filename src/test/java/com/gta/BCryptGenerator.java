package com.gta;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt 비밀번호 암호화 값 생성용 클래스
 * 
 * DB에 사용자 계정을 직접 추가할 때 사용할
 * 비밀번호의 BCrypt 암호화 값을 생성한다.
 */
public class BCryptGenerator {
	public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "1234"; // 생성할 때만 원하는 비밀번호로 변경
        String encodedPassword = encoder.encode(password);

        System.out.println(encodedPassword);
    }
}
