package com.shellwe.back.entity.websocket;

import com.shellwe.back.entity.Member;
import com.shellwe.back.entity.TimeTracker;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Message extends TimeTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private Long id;

    @Column
    private String payload;

    @Column(columnDefinition = "boolean default true")
    private boolean unread;

    @Column
    private boolean notification;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "room_id")
    private Room room;
}