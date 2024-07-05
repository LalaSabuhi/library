package com.informix.bookstore.service;

import com.informix.bookstore.entity.UsersType;
import com.informix.bookstore.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersTypeService {
    private final UsersTypeRepository usersTypeRepository;
    @Autowired
    public UsersTypeService(UsersTypeRepository usersTypeRepository){
        this.usersTypeRepository=usersTypeRepository;
    }

}