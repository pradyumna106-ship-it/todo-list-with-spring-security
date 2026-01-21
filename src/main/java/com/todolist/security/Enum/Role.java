package com.todolist.security.Enum;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private String value;
    
    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
	@Override
	public @Nullable String getAuthority() {
		// TODO Auto-generated method stub
		return name();
	}

}
