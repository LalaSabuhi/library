package com.informix.bookstore.repository;

import com.informix.bookstore.entity.BookPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookPostActivityRepository extends JpaRepository<BookPostActivity, Integer> {
    @Query("SELECT b FROM BookPostActivity b WHERE " +
            "(:title IS NULL OR b.bookTitle LIKE %:title%) AND " +
            "(:startDate IS NULL OR b.postedDate >= :startDate) AND " +
            "(:endDate IS NULL OR b.postedDate <= :endDate) AND " +
            "(:genre IS NULL OR b.genre = :genre)")
    List<BookPostActivity> findBooks(@Param("title") String title,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate,
                                     @Param("genre") String genre);}
