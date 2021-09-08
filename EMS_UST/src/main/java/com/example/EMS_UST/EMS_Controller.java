package com.example.EMS_UST;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EMS_Controller {

AtomicInteger counter = new AtomicInteger();
@Autowired
EMSRepository repository;

@Autowired
EMSAddService addService;

//ADD EMPLOYEE
@PostMapping("/addEmployee")
public ResponseEntity<AddResponse> addEmployeeImplementation(@RequestBody EMS ems) {
	
	String id = ems.getEname()+counter.getAndIncrement();
	HttpHeaders headers = new HttpHeaders();
	//now to get the response , we will create one bean class with ID and msg (class name AddResponse)
		AddResponse add= new AddResponse();
	
	if(!addService.checkEmployeeAlreadyExists(id))
	{
	ems.setEid(id);
	repository.save(ems);
	
	//add.setId(ems.geteName()+counter.getAndIncrement());
	add.setId(id);
	add.setMsg("Success : Employee is Added");
	headers.add("Unique", id);	
	return new ResponseEntity<AddResponse>(add,headers,HttpStatus.CREATED);
	}
	else
	{
		add.setId(id);
		add.setMsg("FAILED : Employee Already Exists");
		headers.add("Unique", id);	
		return new ResponseEntity<AddResponse>(add,headers,HttpStatus.ACCEPTED);
	}
	
}

//Get emp by path param
//FETCH BY ID
 @GetMapping("/getEmployee/{id}")
 public EMS getEmployeeById(@PathVariable(value="id")String id)
 {
	 HttpHeaders headers = new HttpHeaders();
	try {
		EMS ems = repository.findById(id).get();
		return ems;
		
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	} 
	
 }
 
 
 //Get employee by query param
 //FETCH BY NAME
//1. Go to EMSRepository and add EMSRepositoryCustom
//public interface EMSRepository extends JpaRepository<EMS,String>, EMSRepositoryCustom

//2.Right click on EMSRepositoryCustom and create interface
//3. Add List<EMS> findAllByEmpName(String eName); to interface
//4.Create one class called EMSRespositoryImpl
//5. add implements EMSRepositoryCustom to this class and add unimplemented method
//6. Autowire EMSRepository in that class
//7. write logic for findAllByEmpName method in EMSRespositoryImpl
//8. Write GetMapping logic in Controller class
 
 @GetMapping("/getEmployee")
 public List<EMS> getEmployeeByName(@RequestParam(value="EmployeeName")String EmployeeName)
 {
	return repository.findAllByEmpName(EmployeeName);
 
 }
 
 
 //Update employee by path param
 //Create method with PutMApping and pass argument
 
 @PutMapping("/updateEmployee/{id}")
 public ResponseEntity<EMS> updateEmployee(@PathVariable(value = "id")String id,@RequestBody EMS ems)
 {
	 EMS empRecord = repository.findById(id).get();
	 empRecord.setEname(ems.getEname());
	 empRecord.setEsal(ems.getEsal());
	 repository.save(empRecord);
	 
	 return new ResponseEntity<EMS>(empRecord,HttpStatus.OK);
 }
 
 @DeleteMapping("/deleteEmployee")
public ResponseEntity<String> deleteEmployee(@RequestBody EMS ems)
{
	 EMS delRecord = repository.findById(ems.getEid()).get();
	 repository.delete(delRecord);
	 
	 return new ResponseEntity<>("Employee Record Deleted", HttpStatus.CREATED);
}
 
@DeleteMapping("/deleteAll")
public ResponseEntity<String> deleteAll()
{
	repository.deleteAll();
	
	return new ResponseEntity<>("All Employee Record Deleted", HttpStatus.CREATED);
}

}
