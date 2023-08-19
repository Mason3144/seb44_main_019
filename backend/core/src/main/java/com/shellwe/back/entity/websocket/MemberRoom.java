package com.shellwe.back.entity.websocket;

import com.shellwe.back.entity.Member;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Setter
public class MemberRoom {
    public MemberRoom(Room room) {
        this.room = room;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ROOM_ID")
    private Long id;

    @Column
    private boolean active = true;

    @Column
    private Long myShellId;

    @Column
    private Long traderShellId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "room_id")
    private Room room;
    public void setMember(Member member){
        this.member = member;
        member.setMemberRooms(this);
    }
    public void setRoom(Room room){
        this.room = room;
        room.setMemberRooms(this);
    }
}