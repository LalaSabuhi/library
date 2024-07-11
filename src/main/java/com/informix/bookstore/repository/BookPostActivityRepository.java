package com.informix.bookstore.repository;

import com.informix.bookstore.entity.BookPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPostActivityRepository extends JpaRepository<BookPostActivity, Integer> {
}
