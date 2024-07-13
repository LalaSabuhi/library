package com.informix.bookstore.controller;

import com.informix.bookstore.entity.BookPostActivity;
import com.informix.bookstore.service.BookPostActivityService;
import com.informix.bookstore.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BookPostActivityController {
    private final UsersService usersService;
    private final BookPostActivityService bookPostActivityService;
    @Autowired
    public BookPostActivityController(UsersService usersService, BookPostActivityService bookPostActivityService) {
        this.usersService = usersService;
        this.bookPostActivityService = bookPostActivityService;
    }



    @GetMapping("/dashboard/")
    public String searchBooks(Model model,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "author", required = false) String author,
                              @RequestParam(value = "genre", required = false) String genre,
                              @RequestParam(value = "dateRange", required = false) String dateRange) {

        // Set model attributes
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("genre", genre);
        model.addAttribute("dateRange", dateRange);

        // Determine the date range
        LocalDate startDate = null;
        LocalDate endDate = LocalDate.now();
        if ("today".equals(dateRange)) {
            startDate = endDate;
        } else if ("days7".equals(dateRange)) {
            startDate = endDate.minusDays(7);
        } else if ("days30".equals(dateRange)) {
            startDate = endDate.minusDays(30);
        }
        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
        }

        // Perform search
        List<BookPostActivity> books = bookPostActivityService.searchBooks(title, startDate, endDate, genre);
        model.addAttribute("user", currentUserProfile);

        model.addAttribute("books", books);

        return "dashboard";
    }
}
