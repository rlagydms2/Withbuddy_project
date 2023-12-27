package com.lec.spring.withbuddy_project.service;

import com.lec.spring.withbuddy_project.domain.Deshboard;
import org.springframework.ui.Model;

import java.util.List;

public interface DeshboardService {

    List<Deshboard> list();
}
