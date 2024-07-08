package com.informix.bookstore.service;

import com.informix.bookstore.entity.AdminProfile;
import com.informix.bookstore.repository.AdminProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminProfileService {
    private final AdminProfileRepository adminProfileRepository;
    @Autowired
    public AdminProfileService(AdminProfileRepository adminProfileRepository){
        this.adminProfileRepository=adminProfileRepository;
    }
    public Optional<AdminProfile> getOne(int userId) {
        return adminProfileRepository.findById(userId);
    }

    public AdminProfile addNew(AdminProfile adminProfile) {
        return adminProfileRepository.save(adminProfile);
    }
}
