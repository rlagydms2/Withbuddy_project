package com.lec.spring.withbuddy_project.repository;

import com.lec.spring.withbuddy_project.domain.Map;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaploadRepository {
    List<Map> getData();

}
