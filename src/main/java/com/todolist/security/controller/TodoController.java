package com.todolist.security.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.security.Service.TodoService;
import com.todolist.security.dto.TodoListResponse;
import com.todolist.security.dto.TodoRequest;
import com.todolist.security.dto.TodoResponse;
import com.todolist.security.entity.Todo;


@RestController
public class TodoController {
	@Autowired
	private TodoService todoService;

	@PostMapping("/todos")
	public ResponseEntity<TodoResponse> create(@Validated @RequestBody TodoRequest req) {
		Todo todo = todoService.create(req.getTitle(), req.getDescription());
		return ResponseEntity.ok(new TodoResponse(todo.getId(), todo.getTitle(), todo.getDescription()));
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<TodoResponse> update(@PathVariable Long id, @Validated @RequestBody TodoRequest req) {
		Todo todo = todoService.update(id, req.getTitle(), req.getDescription());
		return ResponseEntity.ok(new TodoResponse(todo.getId(), todo.getTitle(), todo.getDescription()));
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		todoService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/todos")
	public ResponseEntity<TodoListResponse> list(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam Optional<String> title,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "desc") String sortDir) {

		int safePage = Math.max(page, 1);
		int safeLimit = Math.min(Math.max(limit, 1), 100);
		Sort sort = "asc".equalsIgnoreCase(sortDir) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(safePage - 1, safeLimit, sort);

		Page<Todo> todos = todoService.list(title, pageable);
		TodoListResponse resp = new TodoListResponse(
				todos.getContent().stream()
						.map(t -> new TodoResponse(t.getId(), t.getTitle(), t.getDescription()))
						.toList(),
				safePage,
				safeLimit,
				todos.getTotalElements());
		return ResponseEntity.ok(resp);
	}
}

