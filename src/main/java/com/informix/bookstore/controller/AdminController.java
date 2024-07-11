package com.informix.bookstore.controller;

import com.informix.bookstore.entity.BookPostActivity;
import com.informix.bookstore.entity.BorrowerProfile;
import com.informix.bookstore.entity.Users;
import com.informix.bookstore.repository.BorrowerProfileRepository;
import com.informix.bookstore.service.BookPostActivityService;
import com.informix.bookstore.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final BorrowerProfileRepository borrowerProfileRepository;
    private final UsersService usersService;
    private final BookPostActivityService bookPostActivityService;

    public AdminController(BorrowerProfileRepository borrowerProfileRepository, UsersService usersService, BookPostActivityService bookPostActivityService) {
        this.borrowerProfileRepository = borrowerProfileRepository;
        this.usersService = usersService;
        this.bookPostActivityService = bookPostActivityService;
    }

    @Autowired


    @GetMapping("/")
    public String adminHome(){
        return "admin";
    }
    @GetMapping("/borrowers")
    public String borrowers(Model model){
        List<BorrowerProfile> borrowerProfiles = borrowerProfileRepository.findAll();
        model.addAttribute("borrowers", borrowerProfiles);
        return "borrowers";
    }
    @GetMapping("/borrowers/delete/{id}")
    public String deleteBorrower(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        borrowerProfileRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Borrower deleted successfully.");
        return "redirect:/admin/borrowers";
    }
    @GetMapping("/books")
    public String books(){
        return "books";
    }

    @GetMapping("/add-books")
    public String addJobs(Model model) {
        model.addAttribute("bookPostActivity", new BookPostActivity());
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-books";
    }

    @PostMapping("/admin/addNew")
    public String addNew(BookPostActivity bookPostActivity, Model model) {

        Users user = usersService.getCurrentUser();
        if (user != null) {
            bookPostActivity.setPostedById(user);
        }
        bookPostActivity.setPostedDate(new Date());
        model.addAttribute("bookPostActivity", bookPostActivity);
        bookPostActivityService.addNew(bookPostActivity);
        return "redirect:/admin/books";
    }
}
