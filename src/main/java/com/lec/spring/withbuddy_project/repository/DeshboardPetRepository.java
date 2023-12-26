package com.lec.spring.withbuddy_project.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeshboardPetRepository {

    List<String> gettypeName();
}
