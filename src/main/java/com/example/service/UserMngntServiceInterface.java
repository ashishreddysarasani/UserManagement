package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.model.ActivateAccount;
import com.example.model.AuthToken;
import com.example.model.MyUserAccount;
import com.example.model.RegDetails;

public interface UserMngntServiceInterface {
	public String isValidEmail(String email);
	public boolean matchPassword(String password);
	public String validateLoginCredentials(AuthToken logintoken);
	public String registerNewAccount(RegDetails user);
	//------------
	public Map<Integer,String> getAllCountries();
	public Map<Integer,String> getStatesByCountry(Integer country);
	public Map<Integer,String> getCitiesByState(Integer state);
	//------------------
	//public DynamicDropdown show();
	//----
	public void sendEmail(String receiverEmail,String otp);
	public String activateAccount(ActivateAccount accActivation);
	public String validateOTP(String email,String opt);
	public String generateOTP(String email);
	public String forgotPassword(String email);
	public boolean isOTPExpired(MyUserAccount mua);
}
