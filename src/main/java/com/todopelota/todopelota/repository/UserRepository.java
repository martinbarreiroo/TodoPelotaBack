package com.todopelota.todopelota.repository;

import com.todopelota.todopelota.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);
}
