package com.informix.bookstore.controller;

import com.informix.bookstore.entity.BorrowerProfile;
import com.informix.bookstore.entity.Users;
import com.informix.bookstore.repository.BorrowerProfileRepository;
import com.informix.bookstore.repository.UsersRepository;
import com.informix.bookstore.service.BorrowerProfileService;
import com.informix.bookstore.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/borrower-profile")
public class BorrowerProfileController {
    private final UsersRepository usersRepository;
    private final BorrowerProfileService borrowerProfileService;
    @Autowired
    public BorrowerProfileController(UsersRepository usersRepository,BorrowerProfileService borrowerProfileService) {
        this.usersRepository = usersRepository;
        this.borrowerProfileService = borrowerProfileService;
    }

    @GetMapping("/")
    public String borrowerProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("could not found user"));
            Optional<BorrowerProfile> borrowerProfile = borrowerProfileService.getOne(users.getUserId());
            if(!borrowerProfile.isEmpty()){
                model.addAttribute("profile" , borrowerProfile.get());
            }

        }
        return "borrower_profile";

    }
    @PostMapping("/addNew")
    public String addNew(BorrowerProfile borrowerProfile,
                         @RequestParam("image")MultipartFile image,
                         @RequestParam("pdf") MultipartFile pdf,
                         Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String curretUsername = authentication.getName();
            Users users = usersRepository.findByEmail(curretUsername).orElseThrow(() -> new UsernameNotFoundException("could not found user"));
            borrowerProfile.setUserId(users);
            borrowerProfile.setUserAccountId(users.getUserId());

        }
        model.addAttribute("profile", borrowerProfile);
        String imageName = "";
        String resumeName = "";

        if (!Objects.equals(image.getOriginalFilename(), "")) {
            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            borrowerProfile.setProfilePhoto(imageName);
        }

        if (!Objects.equals(pdf.getOriginalFilename(), "")) {
            resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
            borrowerProfile.setIdCardPdf(resumeName);
        }

        BorrowerProfile savedUser = borrowerProfileService.addNew(borrowerProfile);

        try {
            String uploadDir = "photos/borrower/" + borrowerProfile.getUserAccountId();
            if (!Objects.equals(image.getOriginalFilename(), "")) {
                FileUploadUtil.saveFile(uploadDir, imageName, image);
            }
            if (!Objects.equals(pdf.getOriginalFilename(), "")) {
                FileUploadUtil.saveFile(uploadDir, resumeName, pdf);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return "redirect:/dashboard/";
    }

}
