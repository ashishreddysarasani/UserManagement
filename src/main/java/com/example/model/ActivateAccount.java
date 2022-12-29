package com.example.model;

import lombok.Data;

@Data
public class ActivateAccount {
	public String email;
	public String tmpPassword;
	public String newPassword;
	public String confirmNewPassword;
}
