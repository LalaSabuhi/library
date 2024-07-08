package com.informix.bookstore.service;

import com.informix.bookstore.entity.AdminProfile;
import com.informix.bookstore.entity.Users;
import com.informix.bookstore.repository.AdminProfileRepository;
import com.informix.bookstore.repository.BorrowerProfileRepository;
import com.informix.bookstore.entity.BorrowerProfile;
import com.informix.bookstore.entity.AdminProfile;
import com.informix.bookstore.repository.UsersRepository;
import com.informix.bookstore.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final UsersTypeRepository usersTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final BorrowerProfileRepository borrowerProfileRepository;
    private final AdminProfileRepository adminProfileRepository;
    @Autowired
    public UsersService(UsersRepository usersRepository, UsersTypeRepository usersTypeRepository, PasswordEncoder passwordEncoder, BorrowerProfileRepository borrowerProfileRepository, AdminProfileRepository adminProfileRepository) {
        this.usersRepository = usersRepository;
        this.usersTypeRepository = usersTypeRepository;
        this.passwordEncoder = passwordEncoder;
        this.borrowerProfileRepository = borrowerProfileRepository;
        this.adminProfileRepository = adminProfileRepository;
    }
    public Users addNew(Users user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setUserTypeId(usersTypeRepository.getReferenceById(2));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = usersRepository.save(user);
        int userTypeId = user.getUserTypeId().getUserTypeId();

        if (userTypeId == 2) {
            borrowerProfileRepository.save(new BorrowerProfile(savedUser));
        }
        else {
            adminProfileRepository.save(new AdminProfile(savedUser));
        }
        return usersRepository.save(user);
    }
    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found " + "user"));
            int userId = users.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("borrower"))) {
                BorrowerProfile borrowerProfile = borrowerProfileRepository.findById(userId).orElse(new BorrowerProfile());
                return borrowerProfile;
            } else {
                AdminProfile adminProfile = adminProfileRepository.findById(userId).orElse(new AdminProfile());
                return adminProfile;
            }
        }

        return null;
    }

}
