package com.shellwe.back.repository;

import com.shellwe.back.entity.Member;
import com.shellwe.back.entity.websocket.MemberRoom;
import com.shellwe.back.entity.websocket.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface MemberRoomRepository extends JpaRepository<MemberRoom, Long> {
    @Query(value = "select member_room.* from member_room\n" +
            "inner join (select room_id from member_room where member_id = ?1 and active = true) as a on a.room_id = member_room.room_id \n" +
            "where member_id != ?1", nativeQuery = true)
    List<MemberRoom> findAllMyRoomsWithSeller(Long memberId);
    Optional<MemberRoom> findByRoomAndMemberAndActiveTrue(Room room, Member member);
    Optional<MemberRoom> findByRoom(Room room);

    Optional<MemberRoom> findFirstByMyShellIdAndTraderShellId(Long myShellId, Long traderShellId);
}
