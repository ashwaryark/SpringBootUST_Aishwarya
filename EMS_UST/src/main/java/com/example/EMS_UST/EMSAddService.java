package com.example.EMS_UST;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EMSAddService {
	
	@Autowired
	EMSRepository repository;
	
	public boolean checkEmployeeAlreadyExists(String id)
	{
		Optional<EMS> ems = repository.findById(id);
		if(ems.isPresent())
		{
			return true;
		}
		else
		{	
		return false;
		}
	}
	
}
