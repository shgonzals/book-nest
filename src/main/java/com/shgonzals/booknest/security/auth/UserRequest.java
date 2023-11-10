package com.shgonzals.booknest.security.auth;

import com.shgonzals.booknest.security.user.Role;
import lombok.Data;

@Data
public class UserRequest {

	private String firstname;
	private String lastname;
	private String email;
	private String password;

}
