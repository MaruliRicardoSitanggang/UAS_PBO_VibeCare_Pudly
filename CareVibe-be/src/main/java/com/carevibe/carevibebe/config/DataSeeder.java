package com.carevibe.carevibebe.config;

import com.carevibe.carevibebe.entity.User;
import com.carevibe.carevibebe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {

        // ===== ADMIN =====
        if (userService.findByEmail("admin@vibecare.com").isEmpty()) {
            User admin = new User();
            admin.setUsername("Super Admin");
            admin.setEmail("admin@vibecare.com");
            admin.setPassword("admin123");
            admin.setRole("ROLE_ADMIN");
            userService.registerUser(admin);
            System.out.println("✅ Admin seeded");
        }

        // ===== PSIKOLOG UTAMA =====
        if (userService.findByEmail("psikolog@vibecare.com").isEmpty()) {
            User psikolog = new User();
            psikolog.setUsername("Dr. Psikolog");
            psikolog.setEmail("psikolog@vibecare.com");
            psikolog.setPassword("psikolog123");
            psikolog.setRole("ROLE_PSIKOLOG");
            userService.registerUser(psikolog);
            System.out.println("✅ Psikolog seeded");
        }

        // ===== PSIKOLOG DARI CONSULTATION VIEW =====
        if (userService.findByEmail("sari.indah@vibecare.com").isEmpty()) {
            User p = new User();
            p.setUsername("Dr. Sari Indah, M.Psi.");
            p.setEmail("sari.indah@vibecare.com");
            p.setPassword("psikolog123");
            p.setRole("ROLE_PSIKOLOG");
            userService.registerUser(p);
            System.out.println("✅ Dr. Sari Indah seeded");
        }

        if (userService.findByEmail("andi.pratama@vibecare.com").isEmpty()) {
            User p = new User();
            p.setUsername("Dr. Andi Pratama, M.Psi.");
            p.setEmail("andi.pratama@vibecare.com");
            p.setPassword("psikolog123");
            p.setRole("ROLE_PSIKOLOG");
            userService.registerUser(p);
            System.out.println("✅ Dr. Andi Pratama seeded");
        }

        if (userService.findByEmail("maya.sari@vibecare.com").isEmpty()) {
            User p = new User();
            p.setUsername("Dr. Maya Sari, M.Psi., Psikolog");
            p.setEmail("maya.sari@vibecare.com");
            p.setPassword("psikolog123");
            p.setRole("ROLE_PSIKOLOG");
            userService.registerUser(p);
            System.out.println("✅ Dr. Maya Sari seeded");
        }

        if (userService.findByEmail("budi.santoso@vibecare.com").isEmpty()) {
            User p = new User();
            p.setUsername("Dr. Budi Santoso, M.Psi.");
            p.setEmail("budi.santoso@vibecare.com");
            p.setPassword("psikolog123");
            p.setRole("ROLE_PSIKOLOG");
            userService.registerUser(p);
            System.out.println("✅ Dr. Budi Santoso seeded");
        }
    }
}