package com.shellwe.back.repository;

import com.shellwe.back.entity.websocket.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
