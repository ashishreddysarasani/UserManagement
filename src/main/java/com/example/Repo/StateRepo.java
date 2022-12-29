package com.example.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//import com.example.model.Country;
import com.example.model.State;

public interface StateRepo extends JpaRepository<State ,Integer>{

	public List<State> findByCid(Integer cid);
}
