package com.example.withbuddy_project.service;

import com.example.withbuddy_project.repository.DeshboardPetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeshboardPetServiceImpl implements DeshboardPetService{

    private final DeshboardPetRepository deshboardPetRepository;


    public DeshboardPetServiceImpl(DeshboardPetRepository deshboardPetRepository){
        this.deshboardPetRepository = deshboardPetRepository;
    }

    @Override
    public List<String> typeName() {

        List<String> typeName = deshboardPetRepository.gettypeName();
        return typeName;
    }

    @Override
    public List<Integer> petcount(){
            List<Integer> result = new ArrayList<>();
            List<String> category = deshboardPetRepository.gettypeName();
        for (String p_category : category) {
            var count = 0;
            for(String a_category : category){
                if(p_category.equals(a_category)) count++;
            }
            result.add(category.indexOf(p_category),count);
        }
        return result;
    }
}
