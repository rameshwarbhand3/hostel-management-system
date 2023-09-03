package com.ram.hms.service;

import java.util.List;

import com.ram.hms.dto.StudentDto;

public interface IStudentService {	
	StudentDto viewStudent();
	List<StudentDto> displayStudents();
	void updateStudent(StudentDto studentDto);
	void deleteStudent(StudentDto studentDto);
	public void addStudent(StudentDto studentDto);
}
