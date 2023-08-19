package com.shellwe.back.entity.websocket;

import com.shellwe.back.entity.TimeTracker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Room extends TimeTracker {
    public Room(Long roomId) {
        this.id = roomId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_ID")
    private Long id;

    @OneToMany(mappedBy = "room", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MemberRoom> memberRooms = new ArrayList<>();

    public void setMemberRooms(MemberRoom memberRoom){
        this.memberRooms.add(memberRoom);
        if (memberRoom.getRoom() != this) memberRoom.setRoom(this);
    }

    public void setMessages(Message message){
        this.messages.add(message);
        if (message.getRoom() != this) message.setRoom(this);
    }
}