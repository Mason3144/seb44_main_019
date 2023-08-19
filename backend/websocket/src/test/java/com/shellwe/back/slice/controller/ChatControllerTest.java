package com.shellwe.back.slice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shellwe.back.controller.ChatController;
import com.shellwe.back.customAnnotation.WithMockCustomUser;
import com.shellwe.back.dto.MemberDto;
import com.shellwe.back.dto.ResponseDto;
import com.shellwe.back.dto.RoomDto;
import com.shellwe.back.service.HttpService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import org.springframework.restdocs.payload.JsonFieldType;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;

@WebMvcTest(ChatController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ChatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HttpService httpService;
    @Autowired
    private ObjectMapper objectMapper;

    private static Map<String,String> headers = new HashMap<>();

    @BeforeAll
    static void init() {
        headers.put("Authorization","Bearer qwmdkqlmdkaend.qmnedkmqwkdmqkwmdk...");
        headers.put("Refresh","qdwnjqnwdjc.ewhsndjnq...");
    }

    @Test
    @WithMockCustomUser
    void getRoomTest() throws Exception {
        MemberDto.Response memberResponse =  MemberDto.Response.builder().id(1L).profileUrl("empty").displayName("홍길동").build();

        RoomDto.Response roomResponse = RoomDto.Response.builder()
                .id(1L)
                .unread(1L)
                .lastMessage("마지막 메세지")
                .member(memberResponse)
                .build();

        given(httpService.findAllRoom()).willReturn(List.of(roomResponse));

        ResultActions actions = mockMvc.perform(
                get("/chat")
                        .header("Authorization", headers.get("Authorization"))
                        .header("Refresh", headers.get("Refresh"))
        );

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(roomResponse.getId()))
                .andExpect(jsonPath("$.[0].unread").value(roomResponse.getUnread()))
                .andExpect(jsonPath("$.[0].lastMessage").value(roomResponse.getLastMessage()))
                .andExpect(jsonPath("$.[0].member.id").value(roomResponse.getMember().getId()))
                .andExpect(jsonPath("$.[0].member.profileUrl").value(roomResponse.getMember().getProfileUrl()))
                .andExpect(jsonPath("$.[0].member.displayName").value(roomResponse.getMember().getDisplayName()))

                .andDo(document("get-rooms",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Access token").attributes(key("constraints").value("Start with 'Bearer'")),
                                headerWithName("Refresh").description("Refresh token")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("룸 식별자"),
                                fieldWithPath("[].unread").type(JsonFieldType.NUMBER).description("읽지 않은 메세지 수"),
                                fieldWithPath("[].lastMessage").type(JsonFieldType.STRING).description("마지막 메세지"),
                                fieldWithPath("[].member.id").type(JsonFieldType.NUMBER).description("채팅 상대 멤버 식별자"),
                                fieldWithPath("[].member.profileUrl").type(JsonFieldType.STRING).description("채팅 상대 멤버 사진 URL"),
                                fieldWithPath("[].member.displayName").type(JsonFieldType.STRING).description("채팅 상대 멤버 이름")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void deleteRoomTest() throws Exception {
        ResultActions actions = mockMvc.perform(
                delete("/chat/{roomId}",1L)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header("Authorization", headers.get("Authorization"))
                        .header("Refresh", headers.get("Refresh"))
        );
        actions.andExpect(status().isNoContent())
                .andDo(document("delete-room",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Access token").attributes(key("constraints").value("Start with 'Bearer'")),
                                headerWithName("Refresh").description("Refresh token")
                        ),
                        pathParameters(
                                parameterWithName("roomId").description("댓글 식별자")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    void createRoomTest() throws Exception {
        RoomDto.Post roomDto = RoomDto.Post.builder()
                .myShellId(1L)
                .sellerShellId(2L)
                .sellerMemberId(2L)
                .build();

        String content = objectMapper.writeValueAsString(roomDto);

        ResponseDto response = ResponseDto.builder()
                .roomsUrl("http://localhost:8080/chat")
                .roomUrl("ws://localhost:8080/chat?roomId=1")
                .build();

        given(httpService.createRoom(Mockito.any())).willReturn(response);

        ResultActions actions = mockMvc.perform(
                post("/chat")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .header("Authorization", headers.get("Authorization"))
                        .header("Refresh", headers.get("Refresh"))
        );

        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomsUrl").value(response.getRoomsUrl()))
                .andExpect(jsonPath("$.roomUrl").value(response.getRoomUrl()))

                .andDo(document("post-room",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Access token").attributes(key("constraints").value("Start with 'Bearer'")),
                                headerWithName("Refresh").description("Refresh token")
                        ),
                        requestFields(
                                fieldWithPath("myShellId").type(JsonFieldType.NUMBER).description("거래할 나의 쉘 식별자"),
                                fieldWithPath("sellerShellId").type(JsonFieldType.NUMBER).description("거래할 상대방의 쉘 식별자"),
                                fieldWithPath("sellerMemberId").type(JsonFieldType.NUMBER).description("거래할 대상의 멤버 식별자")
                        ),
                        responseFields(
                                fieldWithPath("roomsUrl").type(JsonFieldType.STRING).description("현재 멤버의 활성화된 모든 채팅방 조회 API URL"),
                                fieldWithPath("roomUrl").type(JsonFieldType.STRING).description("생성된 채팅방 연결 웹소켓 URL")
                        )
                ));
    }
}
