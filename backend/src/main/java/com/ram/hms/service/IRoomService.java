package com.ram.hms.service;

import java.util.List;

import com.ram.hms.dto.RoomDto;

public interface IRoomService {	
	void addRoomToHostel(RoomDto roomDto) throws Exception;
	List<RoomDto> displayRooms(long hostelId);
	void updateRoom(RoomDto roomDto);
	void deleteRoom(RoomDto roomDto);
}
