package com.lec.spring.withbuddy_project.service;

import com.lec.spring.withbuddy_project.repository.DeshboardUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeshboardUserServiceImpl implements DeshboardUserService{


    private final DeshboardUserRepository deshboardUserRepository;


    public DeshboardUserServiceImpl(DeshboardUserRepository deshboardUserRepository){
        this.deshboardUserRepository = deshboardUserRepository;
    }

    @Override
    public List<String> address() {
        List<String> address = deshboardUserRepository.getAddressName();
        return address;
    }

    @Override
    public List<Integer> usercount() {
        List<Integer> result = new ArrayList<>();
        List<String> userAddress = deshboardUserRepository.getUserAddress();
        List<String> address = deshboardUserRepository.getAddressName();
        for (String a_address : address) {
            var count = 0;
            for (String u_address : userAddress) {
                if(a_address.equals(u_address)) count++;
            }
            result.add(address.indexOf(a_address),count);
        }
        return result;
    }
}
