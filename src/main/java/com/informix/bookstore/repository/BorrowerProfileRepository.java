package com.informix.bookstore.repository;

import com.informix.bookstore.entity.BorrowerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerProfileRepository extends JpaRepository<BorrowerProfile, Integer> {
}
