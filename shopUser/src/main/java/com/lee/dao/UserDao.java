package com.lee.dao;

import com.lee.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author lee
 * @Date 2021/1/19
 * @Version 1.0
 */
public interface UserDao extends JpaRepository<User, Integer> {
}
