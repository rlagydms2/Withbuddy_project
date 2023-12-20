package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.Map;
import com.example.withbuddy_project.repository.MaploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapServiceImpl implements MapService{


    private final MaploadRepository maploadRepository;

    public MapServiceImpl(MaploadRepository maploadRepository){
        this.maploadRepository = maploadRepository;
    }

    @Override
    public List<Map> locationData() {
        List<Map> locationData = maploadRepository.getData();
        return locationData;
    }
}
