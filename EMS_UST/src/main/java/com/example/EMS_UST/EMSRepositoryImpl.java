package com.example.EMS_UST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class EMSRepositoryImpl implements EMSRepositoryCustom {

	@Autowired
	EMSRepository repository;
	
	@Override
	public List<EMS> findAllByEmpName(String eName) {
		
		List<EMS> eNames = new ArrayList<EMS>();
		List<EMS> records = repository.findAll();
		
		for(EMS items:records) {
			if(items.getEname().equalsIgnoreCase(eName))
			{
				eNames.add(items);
			}
		}
		return eNames;
	}
	

}	