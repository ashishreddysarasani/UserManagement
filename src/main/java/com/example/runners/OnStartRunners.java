package com.example.runners;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Repo.CityRepo;
import com.example.Repo.CountryRepo;
import com.example.Repo.StateRepo;
import com.example.model.City;
import com.example.model.Country;
import com.example.model.State;

@Component
public class OnStartRunners implements CommandLineRunner {

	@Autowired
	private CountryRepo crepo;
	@Autowired
	private StateRepo srepo;
	@Autowired
	private CityRepo ctyrepo;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		crepo.saveAll(
				//Arrays.asList( //JDK 1.2
				List.of( //JDK 9	
						new Country(1,"usa"),
						new Country(2, "ind")
						)
				);
		srepo.saveAll(
				//Arrays.asList( //JDK 1.2
				List.of( //JDK 9	
						new State(1,"alaska",1),
						new State(2,"arizona",1),
						new State(3,"california",1),
						new State(4,"texas",1),
						new State(5,"illinois",1),
						new State(6,"karnataka",2),
						new State(7,"tamilnadu",2),
						new State(8,"A.P",2),
						new State(9,"TG",2),
						new State(10,"Delhi",2)
						)
				);
		ctyrepo.saveAll(
				//Arrays.asList( //JDK 1.2
				List.of( //JDK 9	
						new City(1,"juneua",1),
						new City(2,"phoenix",2),
						new City(3,"los angels",3),
						new City(4,"austin",4),
						new City(5,"chicago",5),
						new City(6,"Banglore",6),
						new City(7,"chennai",7),
						new City(8,"vizag",8),
						new City(9,"hyd",9),
						new City(10,"new Delhi",10)
						
						)
				);
	}

}
