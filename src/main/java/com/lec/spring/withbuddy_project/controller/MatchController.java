package com.lec.spring.withbuddy_project.controller;

import com.lec.spring.withbuddy_project.service.MatchServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MatchController {

    private final MatchServiceImpl matchServiceImpl;


}
