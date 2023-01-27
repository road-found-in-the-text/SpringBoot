package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Member;

import java.security.NoSuchAlgorithmException;

public interface ProxyRepository {
    Member getMemberData(String accessToken) throws NoSuchAlgorithmException;
}
