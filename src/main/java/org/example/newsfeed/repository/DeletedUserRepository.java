package org.example.newsfeed.repository;

import org.example.newsfeed.entity.DeletedUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
}
