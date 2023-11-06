package com.ram.hms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllotmentStatus {	
	private int roomNo;
	private String hostelName;
	private String hostelAddress;
	private String contactPerson;
	private long contactPhone;
	private String allotmentStatus;
	private String paymentStatus;
}
