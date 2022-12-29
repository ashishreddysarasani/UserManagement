package com.example.model;

//import java.sql.Date;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@Data
@Entity
@Table(name = "user_acc")
public class MyUserAccount {

	@javax.persistence.Id
	@GeneratedValue
	public Integer Id;
	public String firstName;
	public String lastName;
	public String email;
	public String phno;
	public Date dob;
	public String gender;
	public String country;
	public String state;
	public String city;
	public String password;
	public Integer enabled;
	public String otptoken;
	public Date tokenRequestedTime;
}
