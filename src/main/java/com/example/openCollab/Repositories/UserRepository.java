package com.example.openCollab.Repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.openCollab.Entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);

	User findByUsername(String username);

	User findByFirstName(String firstName);

	ArrayList<User> findAll();

	

	

}
