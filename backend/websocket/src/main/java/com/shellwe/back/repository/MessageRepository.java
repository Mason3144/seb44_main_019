package com.shellwe.back.repository;

import com.shellwe.back.entity.websocket.Message;
import com.shellwe.back.entity.websocket.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findAllByRoomOrderByIdAsc(Room room);
    @Query(value = "select count(*) from shellweprototype.message m where m.room_id = ?1 and m.member_id != ?2 and unread=true", nativeQuery = true)
    Long unReadCount(long roomId, long memberId);

    @Query
    Message findFirstByRoomOrderByIdDesc(Room room);
}
