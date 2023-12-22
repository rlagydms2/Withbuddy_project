package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.Deshboard;
import org.springframework.ui.Model;

import java.util.List;

public interface DeshboardService {

    List<Deshboard> list(Model model);
}
