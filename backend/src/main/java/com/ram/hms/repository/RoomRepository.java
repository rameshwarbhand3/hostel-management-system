package com.ram.hms.repository;

import java.util.List;

import com.ram.hms.entity.Hostel;
import com.ram.hms.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
	Room findById(long id);
	List<Room> findByHostel(Hostel hostel);
}
