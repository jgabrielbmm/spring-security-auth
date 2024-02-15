package com.bentokoder.bentokoder.services;

import com.bentokoder.bentokoder.models.ApplicationUser;
import com.bentokoder.bentokoder.models.Role;
import com.bentokoder.bentokoder.repositories.RoleRepository;
import com.bentokoder.bentokoder.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApplicationUser registerUser(String username, String password){
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        var user = new ApplicationUser(null, username, encodedPassword, authorities);

        userRepository.save(user);

        return user;
    }


}
