package com.ram.hms.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ram.hms.dto.StudentDto;
import com.ram.hms.model.Payment;
import com.ram.hms.model.Room;
import com.ram.hms.model.Student;
import com.ram.hms.model.User;
import com.ram.hms.payload.AllotmentStatus;
import com.ram.hms.payload.BookingStatus;
import com.ram.hms.repository.PaymentRepository;
import com.ram.hms.repository.RoomRepository;
import com.ram.hms.repository.StudentRepository;
import com.ram.hms.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements IStudentService {

	private StudentRepository studentRepository;
	private UserRepository userRepository;
	private RoomRepository roomRepository;
	private PaymentRepository paymentRepository;

	public StudentServiceImpl(
			StudentRepository studentRepository,
			UserRepository userRepository,
			RoomRepository roomRepository,
			PaymentRepository paymentRepository
			) {
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
		this.paymentRepository = paymentRepository;
	}

	@Override
	public List<StudentDto> displayStudents() {
		List<Student> students = studentRepository.findAll();
		return students.stream().map((student) -> mapToStudentDto(student)).collect(Collectors.toList());
	}

	@Override
	public void addStudent(StudentDto studentDto) {
		Student student = new Student();
		User user = userRepository.findByUserName(studentDto.getUserName());
		System.out.println(user);
		student.setUser(user);
		student.setEmailId(studentDto.getEmailId());
		student.setMobileNo(studentDto.getMobileNo());
		student.setCourse(studentDto.getCourse());
		student.setDateOfBirth(studentDto.getDateOfBirth());
		student.setGender(studentDto.getGender());
		student.setAddress(studentDto.getAddress());
		studentRepository.save(student);
	}

	@Override
	public StudentDto viewStudent() {
		String userName = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		System.out.println("username: "+userName);
		StudentDto studentDto = new StudentDto();
		User user = userRepository.findByUserName(userName);
		System.out.println(user);
		studentDto.setFirstName(user.getFirstName());
		studentDto.setLastName(user.getLastName());
		studentDto.setUserName(user.getUserName());
		Student student = studentRepository.findByUser(user);
		System.out.println(student);
		studentDto.setEmailId(student.getEmailId());
		studentDto.setMobileNo(student.getMobileNo());
		studentDto.setCourse(student.getCourse());
		studentDto.setDateOfBirth(student.getDateOfBirth());
		studentDto.setGender(student.getGender());
		studentDto.setAddress(student.getAddress());

		return studentDto;
	}

	@Override
	public void deleteStudent(StudentDto StudentDto) {
		// TODO Auto-generated method stub

	}

	private StudentDto mapToStudentDto(Student student) {
		System.out.println(student);
		StudentDto studentDto = new StudentDto();
		User user = student.getUser();
		System.out.println(user);
		studentDto.setFirstName(user.getFirstName());
		studentDto.setLastName(user.getLastName());
		studentDto.setUserName(user.getUserName());
		studentDto.setEmailId(student.getEmailId());
		studentDto.setMobileNo(student.getMobileNo());
		studentDto.setCourse(student.getCourse());
		studentDto.setDateOfBirth(student.getDateOfBirth());
		studentDto.setGender(student.getGender());
		studentDto.setAddress(student.getAddress());
		try {
			studentDto.setRoomNo(student.getRoom().getRoomNo());
			studentDto.setHostelName(student.getRoom().getHostel().getName());
		} catch (NullPointerException e) {
			studentDto.setRoomNo(-1);
			studentDto.setHostelName("Not Booked");
		}
		return studentDto;
	}

	@Override
	public void updateStudent(StudentDto studentDto) {
		User user = userRepository.findByUserName(studentDto.getUserName());
		System.out.println(user);
		Student student = studentRepository.findByUser(user);
		student.setEmailId(studentDto.getEmailId());
		student.setMobileNo(studentDto.getMobileNo());
		student.setCourse(studentDto.getCourse());
		student.setDateOfBirth(studentDto.getDateOfBirth());
		student.setGender(studentDto.getGender());
		student.setAddress(studentDto.getAddress());
		studentRepository.save(student);
	}

	public boolean bookRoom(BookingStatus bookingStatus) {
		String userName = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		Payment payment = new Payment();
		User user = userRepository.findByUserName(userName);
		Student student = studentRepository.findByUser(user);

		if (bookingStatus.getTransactionId() != null && student.getRoom() == null) {
			Room room = roomRepository.findById(bookingStatus.getRoomId());
			room.setIsVacant((byte) 0);
			student.setRoom(roomRepository.findById(bookingStatus.getRoomId()));
			studentRepository.save(student);
			payment.setTransactionId(bookingStatus.getTransactionId());
			payment.setStudent(student);
			payment.setTransactionStatus("success");
			payment.setTransactionDate(new java.sql.Date(System.currentTimeMillis()));
			paymentRepository.save(payment);
			roomRepository.save(room);
			return true;
		} else {
			payment.setTransactionId(null);
			payment.setStudent(student);
			payment.setTransactionStatus("fail");
			payment.setTransactionDate(new java.sql.Date(System.currentTimeMillis()));
			paymentRepository.save(payment);
			return false;
		}

	}

	public AllotmentStatus getAllotmentStatus() {
		String userName = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		AllotmentStatus allotmentStatus = new AllotmentStatus();
		User user = userRepository.findByUserName(userName);
		Student student = studentRepository.findByUser(user);
		Payment payment = paymentRepository.findByStudent(student);
		if (payment != null) {
			allotmentStatus.setRoomNo(student.getRoom().getRoomNo());
			allotmentStatus.setHostelName(student.getRoom().getHostel().getName());
			allotmentStatus.setContactPerson(student.getRoom().getHostel().getContactPerson());
			allotmentStatus.setContactPhone(student.getRoom().getHostel().getContactMobileNo());
			allotmentStatus.setHostelAddress(student.getRoom().getHostel().getAddress());
			allotmentStatus.setAllotmentStatus("Allotted successfully");
			allotmentStatus.setPaymentStatus(payment.getTransactionStatus());
			return allotmentStatus;
		}else {
			allotmentStatus.setAllotmentStatus("Not alloted");
			allotmentStatus.setPaymentStatus("Please complete the Payment");
			return allotmentStatus;
		}
	}
}
