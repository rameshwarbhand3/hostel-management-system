package com.ram.hms.repository;

import com.ram.hms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ram.hms.model.User;

public interface StudentRepository extends JpaRepository<Student, Long>{
	Student findByUser(User user);
}
