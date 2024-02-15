package com.bentokoder.bentokoder.controllers;

import com.bentokoder.bentokoder.dtos.RegisterDTO;
import com.bentokoder.bentokoder.models.ApplicationUser;
import com.bentokoder.bentokoder.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApplicationUser> register(@RequestBody RegisterDTO data){
        var user = this.authService.registerUser(data.username(), data.password());
        return ResponseEntity.ok().body(user);
    }
}
