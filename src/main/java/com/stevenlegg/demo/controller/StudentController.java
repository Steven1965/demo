package com.stevenlegg.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stevenlegg.demo.model.Student;
import com.stevenlegg.demo.service.StudentService;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
	
	private final StudentService studentService;
	
	private final static Logger LOG  = LoggerFactory.getLogger(StudentController.class);
	
	
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}


	@RequestMapping ( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}
	
	@RequestMapping ( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void insertNewStudent(@RequestBody Student student) {
		LOG.info("Received Request:" + student);
		studentService.persistNewStudent(student.getId(), student);
	}
}
