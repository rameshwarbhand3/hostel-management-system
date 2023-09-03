package com.ram.hms.service;

import java.util.List;

import com.ram.hms.dto.PaymentDto;

public interface IPaymentService {
	List<PaymentDto> getTransactions();
}
