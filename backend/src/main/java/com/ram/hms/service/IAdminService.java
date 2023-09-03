package com.ram.hms.service;

import com.ram.hms.dto.AdminDto;

public interface IAdminService {	
	AdminDto viewAdmin();
	void updateAdmin(AdminDto adminDto);
	public void addAdmin(AdminDto adminDto);
}
