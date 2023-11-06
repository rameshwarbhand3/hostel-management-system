package com.ram.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ram.hms.entity.Hostel;

public interface HostelRepository extends JpaRepository<Hostel, Long> {
	Hostel findById(long id);
}
