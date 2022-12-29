package com.example.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Country;
//import com.example.model.MyUserAccount;

@Repository
public interface CountryRepo extends JpaRepository<Country ,Integer>{

}
