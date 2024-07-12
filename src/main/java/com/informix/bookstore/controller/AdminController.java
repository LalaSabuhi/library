package com.informix.bookstore.controller;

import com.informix.bookstore.entity.BookPostActivity;
import com.informix.bookstore.entity.BorrowerProfile;
import com.informix.bookstore.entity.Users;
import com.informix.bookstore.repository.BorrowerProfileRepository;
import com.informix.bookstore.service.BookPostActivityService;
import com.informix.bookstore.service.UsersService;
import com.informix.bookstore.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BorrowerProfileRepository borrowerProfileRepository;
    private final UsersService usersService;
    private final BookPostActivityService bookPostActivityService;

    @Autowired
    public AdminController(BorrowerProfileRepository borrowerProfileRepository, UsersService usersService, BookPostActivityService bookPostActivityService) {
        this.borrowerProfileRepository = borrowerProfileRepository;
        this.usersService = usersService;
        this.bookPostActivityService = bookPostActivityService;
    }

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
    public String addBooks(Model model) {
        model.addAttribute("bookPostActivity", new BookPostActivity());
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-books";
    }

    @PostMapping("/addNew")
    public String addNew(@ModelAttribute @Valid BookPostActivity bookPostActivity, BindingResult result,
                         @RequestParam("bookImage") MultipartFile image, Model model) {
        Users user = usersService.getCurrentUser();
        if (user != null) {
            bookPostActivity.setPostedById(user);
        }
        bookPostActivity.setPostedDate(new Date());
        model.addAttribute("bookPostActivity", bookPostActivity);
        BookPostActivity saved = bookPostActivityService.addNew(bookPostActivity);
        return "redirect:/admin/books";
    }

}
