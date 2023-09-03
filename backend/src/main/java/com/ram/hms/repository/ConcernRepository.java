package com.ram.hms.repository;


import com.ram.hms.model.Concern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcernRepository extends JpaRepository<Concern, Long> {
	Concern findById(long id);
}
