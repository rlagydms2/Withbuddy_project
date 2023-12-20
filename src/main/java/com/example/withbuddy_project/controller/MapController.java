package com.example.withbuddy_project.controller;

import com.example.withbuddy_project.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @Autowired
    private final MapService mapService;

    public MapController(MapService mapService){
        this.mapService = mapService;
    }

    @GetMapping("/home")
    public void Mapload(Model model)
    {model.addAttribute("location",mapService.locationData());
    }
}
