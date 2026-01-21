package com.todolist.security.controller;
import org.springframework.web.bind.annotation.RestController;
import com.todolist.security.Service.UserService;
import com.todolist.security.dto.LoginRequest;
import com.todolist.security.dto.MessageResponse;
import com.todolist.security.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {
	private UserService service;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Validated @RequestBody RegisterRequest req) {
		String status = service.register(req);
		if (status.equals("Email already exists")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Email already exists"));
		}
		if (status.equals("Invalid role")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Invalid role"));
		}
		return ResponseEntity.ok(status);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest login) {
		String status = service.login(login);
		if (status.equals("Unauthorized")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Unauthorized"));
		}
		return ResponseEntity.ok(status);
	}
	
}
