package com.stevenlegg.demo.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", course=" + course + "]";
	}
	
	private final UUID id;
	private final String firstName;
	private final String lastName;
	private final Integer age;
	private final String course;
	public Student(
			@JsonProperty("id") UUID id, 
			@JsonProperty("firstName") String firstName, 
			@JsonProperty("lastName") String lastName, 
			@JsonProperty("age") Integer age, 
			@JsonProperty("course") String course) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.course = course;
	}
	
	public UUID getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public Integer getAge() {
		return age;
	}
	public String getCourse() {
		return course;
	}

}
