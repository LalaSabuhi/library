package com.informix.bookstore.service;

import com.informix.bookstore.entity.BookPostActivity;
import com.informix.bookstore.repository.BookPostActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookPostActivityService {
    private  final BookPostActivityRepository bookPostActivityRepository;

    public BookPostActivityService(BookPostActivityRepository bookPostActivityRepository) {
        this.bookPostActivityRepository = bookPostActivityRepository;
    }

    public BookPostActivity addNew(BookPostActivity bookPostActivity){
        return bookPostActivityRepository.save(bookPostActivity);
    }

    public BookPostActivity getOne(int id) {
        return bookPostActivityRepository.findById(id).orElseThrow(()->new RuntimeException("Job not found"));
    }

    public List<BookPostActivity> getAllBooks() {
        return bookPostActivityRepository.findAll();
    }

    public Optional<BookPostActivity> findById(int id) {
        return bookPostActivityRepository.findById(id);
    }
}
