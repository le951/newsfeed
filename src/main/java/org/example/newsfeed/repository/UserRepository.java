package org.example.newsfeed.repository;

import org.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Email은 Unique. List<User> 가 아닌 User
    User findByEmail(String email);
}
