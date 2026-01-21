package com.todolist.security.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.todolist.security.Enum.Role;
import com.todolist.security.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
		Optional<User> findByEmail(String email);
		boolean existsByEmail(String email);

		@Query("SELECT u FROM User u WHERE u.email LIKE :keyword%")
		List<User> searchUser(String keyword);
		List<User> findByAuthoritiesContaining(Role role);
}
