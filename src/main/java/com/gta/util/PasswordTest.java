package com.gta.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String rawPassword = "xogh@91##"; // 원하는 비밀번호
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println(encodedPassword);
    }
}
