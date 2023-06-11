package com.mewocz.storeeverything.repository;

import com.mewocz.storeeverything.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

}