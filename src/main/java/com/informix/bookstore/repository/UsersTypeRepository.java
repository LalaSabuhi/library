package com.informix.bookstore.repository;

import com.informix.bookstore.entity.Users;
import com.informix.bookstore.entity.UsersType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersTypeRepository extends JpaRepository<UsersType, Integer> {
}
