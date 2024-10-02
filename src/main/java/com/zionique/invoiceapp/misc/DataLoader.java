package com.zionique.invoiceapp.misc;

import com.zionique.invoiceapp.models.Role;
import com.zionique.invoiceapp.models.RoleName;
import com.zionique.invoiceapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if roles are already populated
        Optional<Role> adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN);
        Optional<Role> userRole = roleRepository.findByName(RoleName.ROLE_USER);

        // If not found, save them to the database
        if (!adminRole.isPresent()) {
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        }
        if (!userRole.isPresent()) {
            roleRepository.save(new Role(RoleName.ROLE_USER));
        }
    }
}

