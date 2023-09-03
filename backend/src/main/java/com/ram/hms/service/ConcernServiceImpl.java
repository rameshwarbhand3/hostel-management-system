package com.ram.hms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ram.hms.dto.ConcernDto;
import com.ram.hms.model.Concern;
import com.ram.hms.model.Student;
import com.ram.hms.model.User;
import com.ram.hms.repository.ConcernRepository;
import com.ram.hms.repository.StudentRepository;
import com.ram.hms.repository.UserRepository;

@Service
public class ConcernServiceImpl implements IConcernService {	
	
	private ConcernRepository concernRepository;	
	private StudentRepository studentRepository;
	private UserRepository userRepository;	

	public ConcernServiceImpl(ConcernRepository concernRepository, StudentRepository studentRepository,
			UserRepository userRepository) {
		this.concernRepository = concernRepository;
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void addConcern(ConcernDto concernDto) {
		Concern concern = new Concern();
		String userName = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		User user = userRepository.findByUserName(userName);
		Student student = studentRepository.findByUser(user);
		concern.setSubject(concernDto.getSubject());
		concern.setMessage(concernDto.getMessage());
		concern.setStudent(student);
		concernRepository.save(concern);
	}
	
	@Override
	public List<ConcernDto> displayConcerns() {
		List<Concern> concerns =  concernRepository.findAll();
		return concerns.stream().map((concern) -> mapToConcernDto(concern)).collect(Collectors.toList());
	}

	private ConcernDto mapToConcernDto(Concern concern) {
		ConcernDto concernDto = new ConcernDto();
		concernDto.setId(concern.getId());
		concernDto.setMessage(concern.getMessage());
		concernDto.setSubject(concern.getSubject());		
		concernDto.setStudentName(concern.getStudent().getUser().getFirstName());
		try {
			concernDto.setHostelName(concern.getStudent().getRoom().getHostel().getName());
			concernDto.setRoomNo(concern.getStudent().getRoom().getRoomNo());
		} catch (NullPointerException e) {
			concernDto.setHostelName("Not Booked");
			concernDto.setRoomNo(-1);
		}
		return concernDto;
	}
}
