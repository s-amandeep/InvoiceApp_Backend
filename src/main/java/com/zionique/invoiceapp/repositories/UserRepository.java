package com.zionique.invoiceapp.repositories;

import com.zionique.invoiceapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMobile(String mobile);
//    Boolean existsByMobile(String mobile);
}
