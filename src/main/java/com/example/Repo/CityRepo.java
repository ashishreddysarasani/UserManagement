package com.example.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.City;
//import com.example.model.Country;
@Repository
public interface CityRepo extends JpaRepository<City ,Integer>{

	//@Query("select c from City c where c.state_id=?1")
	public List<City> findByStateid(Integer State_id);
}
