package com.todolist.security.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.security.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	Page<Todo> findByOwnerEmail(String email, Pageable pageable);
	Page<Todo> findByOwnerEmailAndTitleContainingIgnoreCase(String email, String title, Pageable pageable);
}

