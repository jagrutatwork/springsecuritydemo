package com.example.springsecuritydemo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecuritydemo.model.Student;

@RestController
public class StudentController {
	
	private List<Student> students = new ArrayList<>(List.of(
			new Student(1, "Jagrut", 89),
			new Student(2, "Mrunmayi", 99)
			));
	
	 
	@GetMapping("/students")	
	public List<Student> getStudents(){
		return students;
	}
	
	@PostMapping("/students")
	public Student addStudent(@RequestBody Student student) {
		students.add(student);
		return student;
	}

}
