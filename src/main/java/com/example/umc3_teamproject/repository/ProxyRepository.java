package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Member;

public interface ProxyRepository {
    Member getMemberData(String accessToken) ;
}
