package com.carevibe.carevibebe.controller;

import com.carevibe.carevibebe.entity.User;
import com.carevibe.carevibebe.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Registrasi berhasil!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email atau username sudah terdaftar!");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");

        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User tidak ditemukan!");
        }

        User user = userOpt.get();
        if (!userService.checkPassword(oldPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password lama tidak sesuai!");
        }

        userService.changePassword(user, newPassword);
        return ResponseEntity.ok("Password berhasil diubah!");
    }

    @PostMapping("/seed-admin")
    public ResponseEntity<String> seedAdmin() {
        // Seed admin
        if (userService.findByEmail("admin@vibecare.com").isEmpty()) {
            User admin = new User();
            admin.setUsername("Super Admin");
            admin.setEmail("admin@vibecare.com");
            admin.setPassword("admin123");
            admin.setRole("ROLE_ADMIN");
            userService.registerUser(admin);
        }

        // Seed psikolog
        if (userService.findByEmail("psikolog@vibecare.com").isEmpty()) {
            User psikolog = new User();
            psikolog.setUsername("Dr. Psikolog");
            psikolog.setEmail("psikolog@vibecare.com");
            psikolog.setPassword("psikolog123");
            psikolog.setRole("ROLE_PSIKOLOG");
            userService.registerUser(psikolog);
        }

        return ResponseEntity.ok("Seed berhasil!");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<User> userOpt = userService.findByEmail(email);

        if (userOpt.isEmpty()) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Email tidak terdaftar!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }

        User user = userOpt.get();
        if (!userService.checkPassword(password, user.getPassword())) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Password salah!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
        }

        Map<String, String> response = new HashMap<>();
        response.put("name", user.getUsername());
        response.put("email", user.getEmail());
        response.put("role", user.getRole());
        return ResponseEntity.ok(response);
    }
}