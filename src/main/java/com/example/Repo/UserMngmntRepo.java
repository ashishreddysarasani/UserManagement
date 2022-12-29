package com.example.Repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import com.example.model.Contact;
import com.example.model.MyUserAccount;
@Repository
public interface UserMngmntRepo extends JpaRepository<MyUserAccount ,Integer>{
	
	public MyUserAccount findByEmail(String email);
	//public MyUserAccount findByEmailandPassword(String email,String password);


}
