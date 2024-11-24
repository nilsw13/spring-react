package com.nilsw13.springreact.repository;

import com.nilsw13.springreact.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
