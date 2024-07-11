package com.coffeeorderproject.repository;

import com.coffeeorderproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findUserByUserIdAndUserPw(String userId, String userPw);

}
