package com.example.withbuddy_project.repository;

import com.example.withbuddy_project.domain.Deshboard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeshboardRepository {

    List<Deshboard> loadUserData();
}
