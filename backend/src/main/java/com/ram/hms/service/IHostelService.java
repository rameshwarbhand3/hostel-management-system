package com.ram.hms.service;

import java.util.List;

import com.ram.hms.dto.HostelDto;

public interface IHostelService {	
	void addHostel(HostelDto hostelDto);
	void updateHostel(HostelDto hostelDto) throws Exception;
	List<HostelDto> displayHostels();
	HostelDto viewHostel(long id);
	void deleteHostel(HostelDto hostelDto);
}
