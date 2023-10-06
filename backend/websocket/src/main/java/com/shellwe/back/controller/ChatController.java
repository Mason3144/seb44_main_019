package com.shellwe.back.controller;

import com.shellwe.back.dto.RoomDto;
import com.shellwe.back.service.HttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
@Valid
public class ChatController {

    private final HttpService httpService;

    @PostMapping
    public ResponseEntity createRoom(@RequestBody RoomDto.Post requestBody) {
        return new ResponseEntity<>(httpService.createRoom(requestBody), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity findAllRoom() { // 로그인 된 유저의 id와 연결된 모든 room 출력
        return new ResponseEntity<>(httpService.findAllRoom(), HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity getRoom(@PathVariable long roomId){
        // unread false까지만 렌더링
        // 이후 ws 연결 단계에서 unread true인 메세지들 렌더링
        return null;
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity deleteRoom(@PathVariable long roomId){
        httpService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}