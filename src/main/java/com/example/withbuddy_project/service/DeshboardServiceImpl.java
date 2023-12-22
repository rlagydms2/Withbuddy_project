package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.Deshboard;
import com.example.withbuddy_project.repository.DeshboardRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeshboardServiceImpl implements DeshboardService{

    private DeshboardRepository deshboardRepository;

    public DeshboardServiceImpl(SqlSession sqlSession){
        deshboardRepository = sqlSession.getMapper(DeshboardRepository.class);
        System.out.println("Deshboard() 생성");
    }
    @Override
    public List<Deshboard> list(Model model) {
        List<Deshboard> list = deshboardRepository.loadUserData();
        model.addAttribute("list",list);
        return list;
    }
}
