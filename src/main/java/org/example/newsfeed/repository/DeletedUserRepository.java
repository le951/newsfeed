package org.example.newsfeed.repository;

import org.example.newsfeed.entity.DeletedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
}
