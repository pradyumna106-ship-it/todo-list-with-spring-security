package com.todolist.security.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.todolist.security.Repository.TodoRepository;
import com.todolist.security.Repository.UserRepository;
import com.todolist.security.entity.Todo;
import com.todolist.security.entity.User;
import com.todolist.security.exception.ForbiddenException;
import com.todolist.security.exception.NotFoundException;

@Service
public class TodoService {
	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private UserRepository userRepository;

	public Todo create(String title, String description) {
		User user = currentUserOrThrow();
		Todo todo = new Todo(title, description, user);
		return todoRepository.save(todo);
	}

	public Todo update(Long id, String title, String description) {
		User user = currentUserOrThrow();
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found"));
		if (!todo.getOwner().getEmail().equalsIgnoreCase(user.getEmail())) {
			throw new ForbiddenException("Forbidden");
		}
		todo.setTitle(title);
		todo.setDescription(description);
		return todoRepository.save(todo);
	}

	public void delete(Long id) {
		User user = currentUserOrThrow();
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found"));
		if (!todo.getOwner().getEmail().equalsIgnoreCase(user.getEmail())) {
			throw new ForbiddenException("Forbidden");
		}
		todoRepository.delete(todo);
	}

	public Page<Todo> list(Optional<String> title, Pageable pageable) {
		String email = currentUserEmail();
		if (title.isPresent() && !title.get().isBlank()) {
			return todoRepository.findByOwnerEmailAndTitleContainingIgnoreCase(email, title.get(), pageable);
		}
		return todoRepository.findByOwnerEmail(email, pageable);
	}

	private String currentUserEmail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new NotFoundException("Not Found");
		}
		return auth.getName();
	}

	private User currentUserOrThrow() {
		String email = currentUserEmail();
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Not Found"));
	}
}

