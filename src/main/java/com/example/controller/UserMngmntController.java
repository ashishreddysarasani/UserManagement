package com.example.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.ActivateAccount;
import com.example.model.AuthToken;
import com.example.model.RegDetails;
import com.example.service.UserMngntServiceImpl;

import lombok.Data;
//import com.example.entity.UserAccount;
//import com.example.service.UserMngntServiceImpl;
//import com.example.service.UserMngntServiceInterface;
@Data
@RestController
public class UserMngmntController {

	@Autowired
	private UserMngntServiceImpl usrMgnt;
	
	@PostMapping("/registeraccount")
	public String regNewAcc(@RequestBody RegDetails regd){
		
		String result=usrMgnt.registerNewAccount(regd);
		return result;
	}
	@PostMapping("/accoountAvtivation")
	public String accountAvtivation(@RequestBody ActivateAccount aAcc) {
		
		return usrMgnt.activateAccount(aAcc);
	}
	@PostMapping("/login")
	public String validateAuthCredentials(@RequestBody AuthToken at) {
		String status = usrMgnt.validateLoginCredentials(at);
		return status;
	}
	@PostMapping("/isValidEmail")
	public String isValidEmail(@RequestParam String email) {
		
		return usrMgnt.isValidEmail(email);
	}
	@PostMapping("/sendmail")
	public String sendmail(@RequestParam String receivermail) {
		//usrMgnt.sendEmail(receivermail);
		return "email functionality not working due to gmail account setting";
	}
	@PostMapping("/forgotpwd")
	public String forgotPassword(@RequestParam String email){
		System.out.println("in forgot handler");
		return usrMgnt.forgotPassword(email);
		
	}
	@PostMapping("/validateotp")
	public String validateOtp(@RequestParam String email,@RequestParam String otp) {
		return usrMgnt.validateOTP(email, otp);
	}
	
	@GetMapping("/getallcountries")
	public Map<Integer,String> getAllCountries() {
		// TODO Auto-generated method stub
		return usrMgnt.getAllCountries();
	}

	
	@GetMapping("/getstates")
	public Map<Integer,String> getStates(@RequestParam Integer country) {
		// TODO Auto-generated method stub
		return usrMgnt.getStatesByCountry(country);
	}

	@GetMapping("/getcities")
	public Map<Integer,String> getCities(@RequestParam Integer state) {
		// TODO Auto-generated method stub
		return usrMgnt.getCitiesByState(state);
	}
}
