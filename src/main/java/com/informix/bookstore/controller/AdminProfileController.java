package com.informix.bookstore.controller;

import com.informix.bookstore.entity.AdminProfile;
import com.informix.bookstore.entity.BorrowerProfile;
import com.informix.bookstore.entity.Users;
import com.informix.bookstore.repository.UsersRepository;
import com.informix.bookstore.service.AdminProfileService;
import com.informix.bookstore.service.UsersService;
import com.informix.bookstore.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/admin-profile")
public class AdminProfileController {
    private final UsersRepository usersRepository;
    private final AdminProfileService adminProfileService;
    private  final UsersService usersService;
    @Autowired
    public AdminProfileController(UsersRepository usersRepository, AdminProfileService adminProfileService, UsersService usersService){
        this.usersRepository= usersRepository;
        this.adminProfileService=adminProfileService;
        this.usersService = usersService;
    }

    @GetMapping("/")
    public String adminProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not " + "found user"));
            Optional<AdminProfile> adminProfile = adminProfileService.getOne(users.getUserId());

            if (!adminProfile.isEmpty())
                model.addAttribute("profile", adminProfile.get());

        }
        return "admin_profile";
    }
    @PostMapping("/addNew")
    public String addNew(AdminProfile adminProfile, @RequestParam("image") MultipartFile multipartFile, Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(()-> new UsernameNotFoundException("could not found user"));
            adminProfile.setUserId(users);
            adminProfile.setUserAccountId(users.getUserId());
        }
        model.addAttribute("profile", adminProfile);
        String fileName = "";
        if(!multipartFile.getOriginalFilename().equals("")){
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            adminProfile.setProfilePhoto(fileName);
        }
        AdminProfile savedUser = adminProfileService.addNew(adminProfile);
        String uploadDir = "photos/admin/"+savedUser.getUserAccountId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/dashboard/";
    }


}
