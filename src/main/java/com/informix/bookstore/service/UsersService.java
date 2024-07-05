package com.informix.bookstore.service;

import com.informix.bookstore.entity.Users;
import com.informix.bookstore.repository.UsersRepository;
import com.informix.bookstore.repository.UsersTypeRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final UsersTypeRepository usersTypeRepository;
    @Autowired
    public UsersService(UsersRepository usersRepository,UsersTypeRepository usersTypeRepository){
        this.usersRepository=usersRepository;
        this.usersTypeRepository=usersTypeRepository;
    }
    public Users addNew(Users user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setUserTypeId(usersTypeRepository.getReferenceById(2));
        return usersRepository.save(user);
    }
    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }
}
