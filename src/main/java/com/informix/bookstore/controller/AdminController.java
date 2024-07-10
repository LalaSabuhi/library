package com.informix.bookstore.controller;

import com.informix.bookstore.entity.BorrowerProfile;
import com.informix.bookstore.repository.BorrowerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final BorrowerProfileRepository borrowerProfileRepository;

    @Autowired
    public AdminController(BorrowerProfileRepository borrowerProfileRepository) {
        this.borrowerProfileRepository = borrowerProfileRepository;
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
}
