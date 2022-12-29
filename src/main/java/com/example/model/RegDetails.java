package com.example.model;

import java.util.Date;

//import javax.persistence.GeneratedValue;

//import org.springframework.stereotype.Component;

import lombok.Data;
@Data
public class RegDetails {
	
	//@javax.persistence.Id
	//@GeneratedValue
	//public Integer Id;
	public String firstName;
	public String lastName;
	public String email;
	public String phno;
	public Date dob;
	public String gender;
	public String country;
	public String state;
	public String city;
	//public Integer enabled;
	//String otptoken;
	//String token_timeinmilis;
}
