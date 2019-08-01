/**
 * 
 */
package com.stevenlegg.demo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.stevenlegg.demo.model.Student;

/**
 * @author steve
 *
 */
@Repository("fakeDao")
public final class FakeDaoImpl implements StudentDao {
	
	private final Map<UUID, Student> database;
	
	

	public FakeDaoImpl() {
		database = new HashMap<>();
		UUID studentUid = UUID.randomUUID();
		Student  student = new Student(studentUid, "Steven", "Legg", 54, "SpringBoot");
		database.put(student.getId(), student);
	}

	@Override
	public int insertNewStudent(UUID studentId, Student student) {
		database.put(studentId, student );
		return 1;
	}

	@Override
	public Student selectStudentById(UUID studentId) {
		return database.get(studentId);
	}

	@Override
	public List<Student> selectAllStudents() {
		return new ArrayList<>(database.values());
	}

	@Override
	public int updateStudentById(UUID studentId, Student updatedStudent) {
		database.put(studentId, updatedStudent);
		return 1;
	}

	@Override
	public int deleteStudentById(UUID studentId) {
		database.remove(studentId);
		return 1;
	}

}
