package com.ram.hms.repository;

import com.ram.hms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ram.hms.entity.User;

public interface StudentRepository extends JpaRepository<Student, Long>{
	Student findByUser(User user);
}
