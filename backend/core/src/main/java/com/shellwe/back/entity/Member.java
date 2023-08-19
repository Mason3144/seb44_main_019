package com.shellwe.back.entity;


import com.shellwe.back.entity.type.Status;
import com.shellwe.back.entity.websocket.MemberRoom;
import com.shellwe.back.entity.websocket.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "MEMBER")
@Entity
public class Member extends TimeTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String email;

    @Column(name = "EMAIL_VERIFICATION_STATUS", nullable = false)
    private Boolean emailVerificationStatus;

    private String password;

    private String displayName;

    private String introduction;

    private String profileUrl = "empty";

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Shell> shells = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MemberRoom> memberRooms = new ArrayList<>();

    public Member(Long id) {
        this.id = id;
    }

    public Member(Member member, String password) {
        this.email = member.getEmail();
        this.emailVerificationStatus = member.getEmailVerificationStatus();
        this.displayName = member.getDisplayName();
        this.shells = member.getShells();
        this.password = password;
    }



    public Member(Long id, String email, Boolean emailVerificationStatus, String password, String displayName, String profileUrl) {
        this.id = id;
        this.email = email;
        this.emailVerificationStatus = emailVerificationStatus;
        this.password = password;
        this.displayName = displayName;
        this.profileUrl = profileUrl;
    }

    public void emailVerificationCompleted() {
        this.emailVerificationStatus = true;
    }

    public void updateMember(String password, PasswordEncoder passwordEncoder,
                             String displayName, String introduction,
                             String profileUrl) {
        if (StringUtils.hasText(password)) {
            this.password = passwordEncoder.encode(password);
        }
        if (StringUtils.hasText(displayName)) {
            this.displayName = displayName;
        }
        if (StringUtils.hasText(introduction)) {
            this.introduction = introduction;
        }
        if (StringUtils.hasText(profileUrl) && !profileUrl.equals("empty")) {
            this.profileUrl = profileUrl;
        }
    }
    public void setMemberRooms(MemberRoom memberRoom){
        this.memberRooms.add(memberRoom);
        if (memberRoom.getMember() != this) memberRoom.setMember(this);
    }

    public void setMessages(Message message){
        this.messages.add(message);
        if (message.getMember() != this) message.setMember(this);
    }

    public void addShell(Shell shell) {
        this.shells.add(shell);
        shell.setMember(this);
    }

    public List<Shell> getActiveList() {
        return this.shells.stream()
                .filter(shell -> shell.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
    }

    public List<Shell> getInActiveList() {
        return this.shells.stream()
                .filter(shell -> shell.getStatus() == Status.INACTIVE)
                .collect(Collectors.toList());
    }

    public boolean isPasswordNull() {
        return this.password == null;
    }

    public void updateOauth(String displayName, String profileUrl) {
        this.displayName = displayName;
        this.profileUrl = profileUrl;
    }
}
