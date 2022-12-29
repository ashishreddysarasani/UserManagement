package com.example.service;

//import java.sql.Date;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.model.ActivateAccount;
import com.example.model.AuthToken;
import com.example.model.City;
import com.example.model.Country;
import com.example.model.MyUserAccount;
import com.example.model.RegDetails;
import com.example.model.State;

import lombok.Data;
import net.bytebuddy.utility.RandomString;

import com.example.Repo.CityRepo;
import com.example.Repo.CountryRepo;
import com.example.Repo.StateRepo;
//import com.example.model.UserAccount;
import com.example.Repo.UserMngmntRepo;
@Service
@Data
public class UserMngntServiceImpl implements UserMngntServiceInterface{

	@Autowired
	private UserMngmntRepo repo;
	
	@Autowired
	private MyMailUtil mailUtil;
	
	@Autowired
	private CountryRepo crepo;
	@Autowired
	private StateRepo srepo;
	@Autowired
	private CityRepo ctyrepo;
	
	
	private static final Integer ACCOUNT_ACTIVATED=1;
	private static final Integer ACCOUNT_DEACTIVATED=0;
	private static final String EMAIL_STATUS="Email Valid";

	@Override
	public String validateLoginCredentials(AuthToken logintoken) {
		// TODO Auto-generated method stub
		
		MyUserAccount mua = repo.findByEmail(logintoken.getEmail());
		if(mua==null) {
			return "Please enter a correct email";
		}
		else if(mua.getEnabled()==ACCOUNT_DEACTIVATED) {
			System.out.println("Please Activate your account by cliking the link sent to your emailId");
			return "Account is Deactivated | please activate link sent in mail";
		}
		else if(!logintoken.getPassword().equals(mua.getPassword())) {
		
			System.out.println(" - login credentials are wrong");
			return "Please Enter a valid password";
			//
		}else {
			return "Your are login successfully";
		}
		
	}
	
	@Override
	public String activateAccount(ActivateAccount aa) {
		
		// from form check new and confirmation pwd are correct
		if(!aa.getNewPassword().equals(aa.getConfirmNewPassword())) {
			System.out.println(aa.getNewPassword()+" "+aa.getConfirmNewPassword());
			return "please enter correct new pwd and confirmation pwd";
		}
		
		MyUserAccount mua = repo.findByEmail(aa.getEmail());
		if(mua==null) return "your account not yet created";
		
		//from db check tmppwd correct or not
		else if(aa.getTmpPassword().equals(mua.getPassword())) {
			
			mua.setPassword(aa.getConfirmNewPassword());
			
			// enabled means activation status
			//sendEmail(aa.getEmail(),null);
			mua.setEnabled(1);
			//account is activating here itself
			System.out.println("your account is activated");
			repo.save(mua);
			
		}
		else {
			return "your given temporary password is wrong";
		}
		
		return "account activation success";
	}

	@Override
	public String registerNewAccount(RegDetails user) {
		// TODO Auto-generated method stub
		MyUserAccount a = new MyUserAccount();
		
		
		
		MyUserAccount mua = repo.findByEmail(user.getEmail());
		if( 
				(user.getEmail() != null)  && 
				( mua !=null ) &&
				( mua.getEmail() == user.getEmail() )
				) {
				//check user already registered
			
			return "email already available";
		}
		else {
		a.setEmail(user.getEmail());
		String passwordGenerate=RandomString.make(8);
		a.setPassword(passwordGenerate);
		a.setFirstName(user.getFirstName());
		a.setLastName(user.getLastName());
		a.setGender(user.getGender());
		a.setDob(user.getDob());
		a.setPhno(user.getPhno());
		a.setCountry(user.getCountry());
		a.setState(user.getState());
		a.setCity(user.getCity());
		
		//set enabled=0 it indicates account is deactivated
		a.setEnabled(0);
		
		//printing temporary password
		System.out.println("print temporary pwd : "+passwordGenerate);
		
		//save user to db
		repo.save(a);
	}
		return a.toString()+" \n  your temporary password is : "+ a.getPassword();
	}

	@Override
	public void sendEmail(String receiverEmail,String otp) {
		// TODO Auto-generated method stub
		//Resource file = new FileSystemResource("D:/Images/SpringBoot630PM_1407021_2.png");

		String otpMessage="please enter your otp to change your password is :"+otp;
		boolean sent = mailUtil.send(
				receiverEmail, //to Address
				null,  //cc[]
				null,  //bcc[]
				"Your OTP for forgot password",  //subject
				otpMessage,        //  email body //"HELLO EMAIL",
				null);      //file -- resource name
		if(sent) 
			System.out.println("SUCCESS");
		else
			System.out.println("FAILED");
	}

	@Override
	public String isValidEmail(String email) {
		// TODO Auto-generated method stub
		
		System.out.println("in invalid email");
		MyUserAccount mua = repo.findByEmail(email);
		if(mua==null) {
			System.out.println("Your Account not found");
			return "Your Account not found";
		}
		else if(mua.getEnabled()==ACCOUNT_ACTIVATED) {
			System.out.println("Your Email Is Valid and password sent to your email");
			return "Email Valid";
		}
		
		return "Your account not activated";
	}
	@Override
	public String generateOTP(String email) {
		// TODO Auto-generated method stub
		System.out.println("in gen otp");
		MyUserAccount mua =repo.findByEmail(email);
		if(mua==null) {
			return "your account not found";
		}
		else {
		String otp = RandomString.make(8);
		mua.setOtptoken(otp);
		System.out.println("otp :"+otp);
		
		mua.setTokenRequestedTime(new Date());
		repo.save(mua);
		System.out.println("OTP  :"+ otp);
		
		//sendEmail(email,otp);
		return "otp printed in console and stored in db and expires in 1 minute  ";
		}
	}

	public String forgotPassword(String email) {
		System.out.println("in forgot service");
		
		if(isValidEmail(email).equals(EMAIL_STATUS)) {
			System.out.println("in condition");
			return generateOTP(email);
		}
		
		System.out.println("after condition ");
		return "email invalid";
		
	}
	@Override
	public boolean isOTPExpired(MyUserAccount mua) {
		// TODO Auto-generated method stub
		
		Long otpRequestTimeInMillis = mua.getTokenRequestedTime().getTime();
		if(otpRequestTimeInMillis + OTP_VALID_DURATION < System.currentTimeMillis()) {
			return false;
		}
		return true;
	}
	@Override
	public boolean matchPassword(String password) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String validateOTP(String email,String opt) {
		// TODO Auto-generated method stub
		MyUserAccount mua =repo.findByEmail(email);
		if(mua.getOtptoken()==null) {
			return "otp validation not possible without otp token"; 
		}
		if(!isOTPExpired(mua)) {
			return "your otp is expired";
		}
		else if(mua.getOtptoken().equals(opt)) {
			return "your otp is correct";
		}
		else {
			return "your entered wrong otp";
		}
		
	}
	private static final long OTP_VALID_DURATION = 1 * 60 * 1000; //1 minute
	@Override
	public Map<Integer,String> getAllCountries() {
		// TODO Auto-generated method stub
		Iterable<Country> itr = crepo.findAll();

		Map<Integer,String> cm = new HashMap<>();
		
		//===Print data using diff core java concepts=============
		//itr.forEach(System.out::println); //using method reference
		//itr.forEach(ob -> System.out.println(ob)); //using Lambda Expression
		Iterator<Country> it = itr.iterator(); //using iterator
		while (it.hasNext()) {
			Country c =  it.next();
			
			cm.put(c.getCid(), c.getCname());
			System.out.println(cm);
		}		return cm;
	}

	@Override
	public Map<Integer,String> getStatesByCountry(Integer country) {
		// TODO Auto-generated method stub
		Iterable<State> itr = srepo.findByCid(country);
		Map<Integer,String> sm = new HashMap<>();
		
		
		//===Print data using diff core java concepts=============
		//itr.forEach(System.out::println); //using method reference
		//itr.forEach(ob -> System.out.println(ob)); //using Lambda Expression
		Iterator<State> it = itr.iterator(); //using iterator
		while (it.hasNext()) {
			State s =  it.next();
			
			sm.put(s.getSid(), s.getSname());
			System.out.println(s.getSid()+" "+ s.getSname());
		}		
		System.out.println(sm);
		return sm;	}

	@Override
	public Map<Integer,String> getCitiesByState(Integer state) {
		// TODO Auto-generated method stub
		Iterable<City> itr = ctyrepo.findByStateid(state);
		Map<Integer,String> cty = new HashMap<>();
		
		//===Print data using diff core java concepts=============
		//itr.forEach(System.out::println); //using method reference
		//itr.forEach(ob -> System.out.println(ob)); //using Lambda Expression
		Iterator<City> it = itr.iterator(); //using iterator
		while (it.hasNext()) {
			City c =  it.next();
			
			cty.put(c.getCtyid(), c.getCname());
			System.out.println(c.getCtyid()+" "+c.getCname());
		}		
		System.out.println(cty);
		return cty;	}
	}
	

