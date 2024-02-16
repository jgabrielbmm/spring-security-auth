package com.bentokoder.bentokoder.services;

import com.bentokoder.bentokoder.dtos.LoginResponseDTO;
import com.bentokoder.bentokoder.models.ApplicationUser;
import com.bentokoder.bentokoder.models.Role;
import com.bentokoder.bentokoder.repositories.RoleRepository;
import com.bentokoder.bentokoder.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

    private final AuthenticationManager authenticationManager;

    private TokenService tokenService;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,TokenService tokenService){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public ApplicationUser registerUser(String username, String password){
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        var user = new ApplicationUser(null, username, encodedPassword, authorities);
        var newUser = userRepository.save(user);
        return newUser;
    }


    public LoginResponseDTO loginUser(String username, String password){
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
        }catch (AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }


}
