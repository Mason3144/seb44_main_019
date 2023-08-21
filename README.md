# 👨‍💻 프로젝트 개요

물품, 재능 교환 서비스 플랫폼 개발

ShellWe는 사용자가 가진 “무언가”를 다른 사용자와 교환하기 위한 서비스 플랫폼입니다.
여기서 그 “무언가”는 사용자가 소유하고 있는 유형의 제품이 될 수도 있으며, 사용자가 가진 재능이라는 무형의 제품도 될 수 있습니다.
이러한 유형 제품인 물품과 무형 제품인 재능을 자유롭게 교환할 수 있는 웹 어플리케이션 입니다.

사용자는 자유롭게 자신의 제품들을 올리고 다른사용자의 제품과 매칭시킨 뒤 DM 기능을 이용하여 자유롭게 교류합니다.

# 🧑🏻‍💻 Member

<table>
<tbody>
    <tr>
        <td>
            <a href="https://github.com/DongwooJoo">
                <img src="https://github.com/DongwooJoo.png" width="70px" />
            </a>
        </td>
        <td>
            <a href="https://github.com/lhs9602">
                <img src="https://github.com/lhs9602.png" width="70px" />
            </a>
        </td>
        <td>
            <a href="https://github.com/Jeongchanyeong">
                <img src="https://github.com/Jeongchanyeong.png" width="70px" />
            </a>
        </td>
        <td>
            <a href="https://github.com/Mason3144">
                <img src="https://github.com/Mason3144.png" width="70px" />
            </a>
        </td>
        <td>
            <a href="https://github.com/Cishcash8725">
                <img src="https://github.com/Cishcash8725.png" width="70px" />
            </a>
        </td>
        <td>
            <a href="https://github.com/tjddnr7760">
                <img src="https://github.com/tjddnr7760.png" width="70px" />
            </a>
        </td>
    </tr>
    <tr>
        <td><p align="center">주동우<br>[팀장/FE]</p></td>
        <td><p align="center">이호섭<br>[FE]</p></td>
        <td><p align="center">정찬영<br>[FE]</p></td>
        <td><p align="center">이태섭<br>[부팀장/BE]</p></td>
        <td><p align="center">김영준<br>[BE]</p></td>
        <td><p align="center">황성욱<br>[BE]</p></td>
    </tr>
</tbody>
</table>

# 📚 STACKS AND TOOLS

<table class="images" width="100%"  style="border:0px solid white; width:100%; vertical-align : top;">
    <tr style="border: 0px;">
        <td style="border:0px; width:25%; height:200px; vertical-align : top;">
            <h2 style="color: #337EA9; padding-top: 0px; margin-top: 0px;">Back</h2>
            <div>&nbsp&nbsp• Java Spring Boot</div>
            <div>&nbsp&nbsp• Spring Security</div>
            <div>&nbsp&nbsp• Spring Websocket</div>
            <div>&nbsp&nbsp• Spring data JPA</div>
            <div>&nbsp&nbsp• MySQL</div>
            <div>&nbsp&nbsp• Google Oauth2</div>
            <div>&nbsp&nbsp• Google SMTP</div>
        </td>
        <td style="border:0px; width:25%; vertical-align : top;">
            <h2 style="color: #D9730D; padding-top: 0px; margin-top: 0px;">Deploy</h2>
            <div>&nbsp&nbsp• AWS S3</div>
            <div>&nbsp&nbsp• AWS EC2</div>
            <div>&nbsp&nbsp• Github Actions</div>
        </td>
        <td style="border:0px; width:25%; vertical-align : top;">
            <h2 style="color: #D44C47; padding-top: 0px; margin-top: 0px;">Front</h2>
            <div>&nbsp&nbsp• JavaScript</div>
            <div>&nbsp&nbsp• React</div>
            <div>&nbsp&nbsp• React Router</div>
            <div>&nbsp&nbsp• Styled-components</div>
        </td>
        <td style="border:0px; width:25%; vertical-align : top;">
            <h2 style="color: #CB912F; padding-top: 0px; margin-top: 0px;">Common</h2>
            <div>&nbsp&nbsp• Git</div>
            <div>&nbsp&nbsp• Github</div>
            <div>&nbsp&nbsp• Notion</div>
            <div>&nbsp&nbsp• Discord</div>
        </td>
    </tr>
</table>

# 📔 테이블 ERD
<img src="https://github.com/Mason3144/github-practice/assets/59563548/659af75b-797b-40f3-bc66-cb5768f4f94a">


# 📌 주요 기능 요약

### 멤버 CRUD
- 사용자의 회원가입(Oauth2 포함), 수정, 조회, 삭제

### 쉘 CRUD
- 쉘 생성, 조회, 수정, 삭제
- 특정 사용자와 관련이 있는 쉘 조회
- 찜하기 (장바구니 기능)

### 교환 요청
- 교환 요청: 쉘에 대한 교환 요청을 보냅니다.
- 요청 수락: 특정 요청에 대한 수락이 가능합니다.

### DM
- 룸(채팅방) 생성, 조회, 삭제
- 이전 메세지 조회 및 전송
- 웹소켓을 이용한 실시간 채팅


# ⚙️ 내가 맡은 기능

**웹소켓 서버 구현**

- Spring Websocket 프레임워크를 활용하여 웹소켓 서버 구현 및 WS 프로토콜 연결 구현. <a href="https://github.com/Mason3144/seb44_main_019/tree/main/backend/websocket" target='_blank'> [링크] </a>

**웹소켓 서버의 사용자 인증**

- TCP의 3 Way-Handshake 단계에서 인증된 사용자 정보를 웹소켓 세션에 저장하여 보관. <a href="https://github.com/Mason3144/seb44_main_019/blob/main/backend/websocket/src/main/java/com/shellwe/back/handler/CustomHandshakeHandler.java" target='_blank'> [링크] </a> 
- 동일 채팅방의 세션을 통해 내 정보 및 상대방 정보에 접근하여 DB 조회 횟수 최소화.

**DM 기능 구현** <a href="https://github.com/Mason3144/seb44_main_019/blob/main/backend/websocket/src/main/java/com/shellwe/back/service/WsChatService.java" target='_blank'> [링크] </a>

- 채팅방 생성 및 삭제 기능(HTTP 프로토콜) 구현. <a href="https://github.com/Mason3144/seb44_main_019/blob/main/backend/websocket/src/main/java/com/shellwe/back/service/HttpService.java" target='_blank'> [링크] </a>
- 사용자가 특정 URL(WS 프로토콜)에 접근 시 유니크한 세션 발급 및 해당 세션을 특정 룸(채팅방)에 입장 구현.
- 사용자가 메세지 입력 시 웹소켓 서버가 해당 룸에 연결된 모든 세션에 해당 메세지 전송 구현.
- 사용자 퇴장 시 연결된 룸에서 해당 사용자의 세션 제거 구현.

**DB 설계 및 활용**

- 사용자, 채팅방, 메세지 간 연관관계 설계 및 구현. <a href="https://github.com/Mason3144/seb44_main_019/tree/main/backend/core/src/main/java/com/shellwe/back/entity/websocket" target='_blank'> [링크] </a>  
- 모든 메세지 기록을 비동기로 DB에 저장하여 사용자에게 빠른 서비스 제공, 이후 채팅방 입장시 이전 메세지들 로딩 구현. <a href="https://github.com/Mason3144/seb44_main_019/blob/main/backend/websocket/src/main/java/com/shellwe/back/service/AsyncService.java" target='_blank'> [링크] </a>
- Thread pool을 이용하여 비동기 작업시 서버의 자원 소모 최적화. <a href="https://github.com/Mason3144/seb44_main_019/blob/main/backend/websocket/src/main/java/com/shellwe/back/config/SpringAsyncConfig.java" target='_blank'> [링크] </a>
- SQL 쿼리문을 사용하여 비지니스 로직 최적화. <a href="https://github.com/Mason3144/seb44_main_019/blob/main/backend/websocket/src/main/java/com/shellwe/back/repository/MemberRoomRepository.java" target='_blank'> [링크] </a>

**환경변수 관리**

- AWS Secret Manager를 활용하여 사용될 환경변수 중앙 관리 구현.
- EC2 인스턴스 기능 장애에 대비하고 보안 강화 구현.




# 📖 개발 내용(개발 당시 고려 사항)
### **서버과부화를 방지하기 위해 웹소켓과 백엔드 서버를 분리**

서버 과부화를 방지하기 위해 저희는 두 가지 주요 기능을 분리하여 설계했습니다. 회원과 게시글과 관련된 기능은 Spring MVC API 서버에서 처리하고, 사용자 매칭 및 웹소켓을 이용한 DM 기능은 별도의 Spring Websocket 서버에서 처리하도록 분리했습니다.

이로써 웹소켓 연결과 관련된 자원이 백엔드 서버의 리소스를 독점하는 것을 방지하여 서버 과부화를 최소화했습니다. 또한 추후 기능 추가 및 에러 발생 시 해당 단일 서버만을 조치할 수 있어 더욱 효율적인 관리가 가능하게끔 설계하였습니다.
### **환경변수 통합 관리**

저희는 AWS Secret Manager를 활용하여 환경 변수를 효율적으로 관리하고, 두 서버 간의 환경 변수 공유를 용이하게 구성했습니다. 이를 통해 향후 서버 이전 시에도 환경 변수 관리를 편리하게 할 수 있습니다.

Secret Manager를 활용하여 각 환경 변수를 암호화된 시크릿으로 저장하고, 서버가 필요할 때마다 해당 시크릿을 가져와 사용합니다.

또한 IAM 롤을 통해 적절한 권한을 관리하고 팀원들에게 Secret Manager를 사용하는 방법과 관련한 문서를 공유하여 팀원들이 효과적으로 환경 변수를 활용하고 관리할 수 있도록 지원하였으며 이를 통해 보안과 운영 효율성을 모두 확보하였습니다.

### **개발 문서 최신화**

저희 팀은 개발 문서들을 항상 최신 상태로 유지하여 회의에서 발생한 수정사항을 신속하게 반영했습니다. 이를 통해 팀 간 의사소통과 협업을 원활하게 하고, 개인의 실수나 오해로 인한 문제를 최소화하였습니다.

- 주요 문서
    - <a href="https://drive.google.com/file/d/1QOQejNBIK81FRUzaMBc4HRVnhUCIgMN2/view?usp=sharing" target="_blank">프로젝트 기획서</a>
    - <a href="https://drive.google.com/file/d/1Hm2JBRdh9ZM45vuzw6N6Ze_T9b_82UvN/view?usp=sharing" target="_blank">깃 컨벤션</a>
    - <a href="https://drive.google.com/file/d/1MJjJz7-uduEgY69PnBdJ4QJbJNjoJucK/view?usp=sharing" target="_blank">API 명세서</a>
- 기타 문서
    - <a href="https://drive.google.com/file/d/1DGmXD13h9hvCdTRy6ygjlWufMOeED8i9/view?usp=sharing" target="_blank">테이블 명세서</a>
    - <a href="https://drive.google.com/file/d/1Z3rpJ-YYRb09Rrp85dHF51dvmqMXOlK1/view?usp=sharing" target="_blank">화면 정의서</a>
    - <a href="https://drive.google.com/file/d/1ryhg52BtxELCGpO9LVSt-hwAg3UMyUVa/view?usp=sharing" target="_blank">개발자 테스트 체크리스트</a>
    - <a href="https://drive.google.com/file/d/1ve7AJY5Br1bZC3rMv1wG9nJeXMa8fF-l/view?usp=sharing" target="_blank">서비스 메뉴얼</a>
    - <a href="https://drive.google.com/file/d/18yYjZBhytMul4DuM2MaQZSGn1lOBNnQR/view?usp=sharing" target="_blank">사용자 요구사항 정의서</a>


# 🔮 향후 보완점 및 회고
1. 멀티 모듈 적용 (완료) <a href="https://mason-lee.tistory.com/161" target='_blank'> [블로그 링크] </a>, <a href="https://github.com/Mason3144/seb44_main_019/blob/main/backend/build.gradle" target='_blank'> [깃허브 링크] </a>
2. ERD 최적화 <a href="https://mason-lee.tistory.com/160" target='_blank'> [링크] </a> 
