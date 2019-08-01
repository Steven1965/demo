package com.stevenlegg.demo.dao;

import java.util.List;
import java.util.UUID;

import com.stevenlegg.demo.model.Student;

public interface StudentDao {

	public int insertNewStudent(UUID studentId, Student student);

	public Student selectStudentById(UUID studentId);

	public List<Student> selectAllStudents();

	public int updateStudentById(UUID studentId, Student updatedStudent);

	public int deleteStudentById(UUID studentId);

}
