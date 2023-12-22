package com.example.withbuddy_project.repository;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeshboardUserRepository {
    List<String> getAddressName();

    List<String> getUserAddress();
}
