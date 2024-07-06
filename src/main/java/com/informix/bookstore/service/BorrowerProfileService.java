package com.informix.bookstore.service;

import com.informix.bookstore.entity.BorrowerProfile;
import com.informix.bookstore.repository.BorrowerProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowerProfileService {
    private final BorrowerProfileRepository borrowerProfileRepository;

    public BorrowerProfileService(BorrowerProfileRepository borrowerProfileRepository) {
        this.borrowerProfileRepository = borrowerProfileRepository;
    }

    public Optional<BorrowerProfile> getOne(Integer id){
        return borrowerProfileRepository.findById(id);
    }

    public BorrowerProfile addNew(BorrowerProfile borrowerProfile) {
        return borrowerProfileRepository.save(borrowerProfile);
    }
}
