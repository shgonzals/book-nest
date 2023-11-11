package com.shgonzals.booknest.security.auth;

import com.shgonzals.booknest.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

	private String firstname;
	private String lastname;
	private String email;
	private String password;

}
