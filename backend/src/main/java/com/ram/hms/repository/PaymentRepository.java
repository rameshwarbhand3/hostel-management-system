package com.ram.hms.repository;

import com.ram.hms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ram.hms.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Payment findById(long id);
	Payment findByStudent(Student student);
}
