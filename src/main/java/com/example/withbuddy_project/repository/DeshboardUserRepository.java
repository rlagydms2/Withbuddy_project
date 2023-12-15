package com.example.withbuddy_project.repository;

import com.example.withbuddy_project.domain.Address;
import com.example.withbuddy_project.domain.User;

import java.util.List;

public interface DeshboardUserRepository {
    List calculateById(User user, Address address);
}
