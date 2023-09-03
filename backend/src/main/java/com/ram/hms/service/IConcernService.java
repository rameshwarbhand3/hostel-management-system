package com.ram.hms.service;

import java.util.List;

import com.ram.hms.dto.ConcernDto;

public interface IConcernService {
	void addConcern(ConcernDto concernDto);
	List<ConcernDto> displayConcerns();
}
