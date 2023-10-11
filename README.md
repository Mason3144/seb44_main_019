# 👨‍💻 프로젝트 개요

물품, 재능 교환 서비스 플랫폼 개발

웹 어플리케이션 주소 : http://shellwe.net/

ShellWe는 사용자가 가진 “무언가”를 다른 사용자와 교환하기 위한 서비스 플랫폼입니다.
여기서 그 “무언가”는 사용자가 소유하고 있는 유형의 제품이 될 수도 있으며, 사용자가 가진 재능이라는 무형의 제품도 될 수 있습니다.
이러한 유형 제품인 물품과 무형 제품인 재능을 자유롭게 교환할 수 있는 웹 어플리케이션 입니다.

사용자는 자유롭게 자신의 제품들을 올리고 다른사용자의 제품과 매칭시킨 뒤 DM 기능을 이용하여 자유롭게 교류합니다.

<br/>

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

<br/>

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

<br/>

# 📔 테이블 ERD
<img src="https://github.com/Mason3144/github-practice/assets/59563548/659af75b-797b-40f3-bc66-cb5768f4f94a">

<br/>

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

<br/>

# ⚙️ 내가 맡은 기능

## 웹소켓 서버 구현 

Spring Websocket 프레임워크를 활용하여 웹소켓 서버의 전체적인 설계 및 기능 구현을 담당하였습니다.

웹소켓 서버의 전체적인 설계와 흐름은 다음과 같습니다.
1. **WebSocketConfigurer** 인터페이스를 이용하여 특정 URI에 대한 웹소켓 프로토콜 접근 허용
2. **TextWebSocketHandler** 클래스를 이용하여 클라이언트가 접근시 고유의 **WebSocketSession**을 부여 합니다. 이후 해당 세션을 비즈니스 계층으로 전달합니다.
3. 전달받은 세션을 통해 클라이언트로 부터 데이터를 전달받고 비즈니스 계층에서는 데이터를 정제 한뒤, 권한이 있는 다른 사용자에게 해당 데이터(메세지)를 전달합니다.
4. 정제된 데이터는 비동기 작업을 통해 데이터베이스 엑세스 계층의 JpaRepository로 전달되어 DB에 저장됩니다.
5. 모든 작업 이후, 클라이언트에서 웹소켓 연결을 종료하면 서버에 보관되어있던 **WebSocketSession**을 삭제합니다.

- <a href="https://github.com/Mason3144/seb44_main_019/tree/main/backend/websocket" target='_blank'> [프로젝트 링크] </a>

## 웹소켓 서버의 사용자 인증
**DefaultHandshakeHandler** 클래스를 이용하여 TCP의 3 Way-Handshake 단계 이후, 인증된 사용자 정보를 웹소켓 세션에 저장하여 보관하도록 설계하였습니다.

사실 프로젝트 기간동안 제일 많은시간이 소비되었고 가장 고심했던 부분이 이 "사용자 인증" 부분이었습니다.

처음 웹소켓 서버를 설계했을 때에는, 사용자가 메세지를 보낼때마다 사용자의 고유정보를 클라이언트로 부터 전달받는 방식으로 설계하였고 두가지 문제점이 발생했습니다.
1. 보안에 취약하다. 매 메세지마다 사용자의 고유정보를 함께보내게 되면서 해당 고유정보를 이용하여 다른 사용자의 채팅방에 간섭할 가능성이 있었습니다.
2. 매 메세지 발신마다 발신자의 정보를 조회하기 위해 DB에 접근해야만 했습니다.

위와 같은 이유로 다음과 같이 사용자 인증 흐름을 변경하게 되었습니다.

1. Spring Security 프레임워크를 설치하여 사용자 인증 시점을 메세지 전송시가 아닌 처음 클라이언트와 서버의 웹소켓 프로토콜이 연결되는 시점으로 변경
2. 클라이언트에서 넘겨받은 JWT를 이용하여 사용자 인증 진행 및 **SecurityContextHolder**에 사용자 정보 저장합니다.
3. 이후 **DefaultHandshakeHandler** 클래스를 이용하여 사용자가 부여받은 **WebSocketSession**의 **Principal** 객체에 사용자 정보를 저장하여 인증을 마칩니다. 

이후 클라이언트가 메세지를 전송할 시, **WebSocketSession**에서 발신자 정보를 조회하여 DB의 접근을 줄이고 보안을 강화할 수 있었습니다

- <a href="https://mason-lee.tistory.com/154" target='_blank'> [문제 해결 블로그 링크] </a>

## DM(실시간 채팅) 기능 구현 
웹소켓 서버의 실질적인 기능 수행은 다음과 같습니다.

- 채팅방 생성, 조회, 삭제 기능(Spring MVC, HTTP 프로토콜) 
- 두 사용자간의 WebSocket 연결 및 연결 해제(Spring WebSocket, WS 프로토콜)
- 두 사용자간의 메세지 입출력(Spring WebSocket, WS 프로토콜)
- 이전 메세지 출력(Spring WebSocket, WS 프로토콜)
- 상대방이 나의 메세지를 확인했는지의 상태 여부 체크 기능(Spring WebSocket, WS 프로토콜, 개발 중단)
- 채팅방들의 내가 읽지 않은 메세지가 존재하는지 체크 기능(Spring WebSocket, WS 프로토콜, 개발 중단)

위와 같은 기능들을 구현하고 나니 서버 분리의 기준을 기본 기능과 DM 기능으로 나누는 것이 아닌, HTTP 프로토콜과 WS 프로토콜로 나누는것이 좋지 않았을까? 하는 아쉬움이 남앗습니다.

- <a href="https://github.com/Mason3144/seb44_main_019/blob/main/backend/websocket/src/main/java/com/shellwe/back/service/WsChatService.java" target='_blank'> [WS 비즈니스 로직] </a> </br>
- <a href="https://github.com/Mason3144/seb44_main_019/blob/main/backend/websocket/src/main/java/com/shellwe/back/service/HttpService.java" target='_blank'> [HTTP 비즈니스 로직] </a>

<img src="https://github.com/codestates-seb/ShellWe/assets/59563548/a1dae7be-c14b-4ca0-a70f-855c05f26c52">


## DB 설계 및 활용
사용자, 채팅방, 메세지(Member, Member_Room, Room, Message 테이블) 간의 연관관계를 설계하고 기능들을 구현하였습니다.

### Multi thread를 이용하여 데이터베이스 엑세스 계층에 접근
특정 채팅방의 이전 메세지들을 불러오기 위해서 발신되는 모든 메세지들을 DB에 저장해야만 했고 실시간 채팅 특성상, 처리속도가 중요하다 생각하여 멀티 쓰레드를 이용하게 되었습니다. </br>
또한 효율적인 자원 관리를 위해 Thread pool을 설정하여 서버의 자원 소모를 최적화 하였습니다.
- <a href="https://github.com/Mason3144/seb44_main_019/blob/main/backend/websocket/src/main/java/com/shellwe/back/config/SpringAsyncConfig.java" target='_blank'> [Thread pool 설정 코드] </a>

추가적으로 SQL문의 서브쿼리를 사용하여 비지니스 로직을 최적화 하였습니다.
- <a href="https://github.com/Mason3144/seb44_main_019/blob/main/backend/websocket/src/main/java/com/shellwe/back/repository/MemberRoomRepository.java" target='_blank'> [서브쿼리 사용 코드] </a>


## 환경변수 관리
이번 ShellWe 프로젝트 이전, AWS EC2 프리티어 버전의 Ubuntu 운영체제에 환경변수를 직접 저장하여 사용한 경험이 있었습니다.</br> 
하지만 저사양 인스턴스인 프리티어 버전의 특성상 서버가 자주 다운이 되고 심지어 마지막에는 인스턴스가 재부팅 조차 되지않아 환경변수를 다시 작성해줘야 되는 일이 발생했습니다.

이번 프로젝트에서는 이러한 점을 방지하기 위해 AWS Secrets Manager를 이용하여 환경변수를 중앙관리 하여 EC2 인스턴스 기능 장애에 대비하고 서버의 확장성을 높였습니다.

<img src="https://github.com/codestates-seb/ShellWe/assets/59563548/d1e0ad5b-17d3-4a0a-8be9-08f5ba528bd1">



## 예외 처리
**@RestControllerAdvice** 어노테이션을 사용하여 발생될 가능성이 있는 예외들을 한곳에 모아 처리하였습니다.</br>
또한 다양한 예외 코드들을 Enum 타입으로 지정하여 유지보수성을 높였습니다.
- <a href="https://github.com/Mason3144/seb44_main_019/tree/main/backend/websocket/src/main/java/com/shellwe/back/exception" target='_blank'> [예외 처리 패키지] </a>

## 테스트
MockMvc를 이용하여 HTTP의 컨트롤러와 서비스레이어에 대한 슬라이스 테스트를 진행하였습니다.
컨트롤러에 대한 테스트 진행시, 테스트를 위한 Mock 유저를 어노테이션으로 만들어 활용하였습니다.

또한 AssertJ를 이용하여 JWT 인증과 MemberDetailsService에 대해 추가적인 유닛 테스트를 진행하였습니다.
- <a href="https://github.com/Mason3144/seb44_main_019/tree/main/backend/websocket/src/test" target='_blank'> [테스트 패키지] </a>

</br>

# 🔮 릴리즈 이후 보완 상황

## 멀티 모듈 적용
초기 프로젝트 기획 당시 WS 프로토콜의 과도한 리소스 사용으로 인한 서버의 과부화를 막기 위해 해당 프로젝트를 
백엔드 서버와 웹소켓 서버로 나누어 설계 및 개발하면서 몇가지 큰 문제점이 발생하였습니다.

1. 코드의 중복이 다량 발생. 동일한 엔티티와 Spring Security 설정을 사용해주기 때문에 많은 양의 코드 중복이 생기게 되었습니다.
2. 한쪽 서버에서 중복된 코드를 조금만 수정하더라도 다른 서버의 코드도 마찬가지로 계속해서 변경해주어야 되었습니다.

그로 인해 작업의 복잡도는 증가하고 작업 속도는 줄어드는 현상이 발생하게 되었습니다.

그렇게 프로젝트가 끝난 뒤 웹소켓과 백엔드로 나눠진 프로젝트를 하나의 프로젝트로 통합,
이후 마이그레이션을 실행할 컴포넌트들의 범위를 정한 뒤 core 모듈을 생성하여 해당 컴포넌트들을 이동시켰습니다. 

- <a href="https://github.com/Mason3144/seb44_main_019/commit/17167b03b50691a91e64a327bb2db8cfca6f20a0" target='_blank'> [커밋 내역] </a>
- <a href="https://mason-lee.tistory.com/161" target='_blank'> [멀티모듈 블로그 링크] </a>

<img src="https://github.com/codestates-seb/ShellWe/assets/59563548/e67e4b73-e14a-4bb8-94b6-6ac21fd6fa7e">

## ERD 재설계
처음 ERD 설계 당시, 모든 연관관계를 양방향으로 설정하여 엔티티의 코드량이 증가하고 가독성도 떨어지며 개발자의 실수를 발생시킬 수 있는 
비효율 적인 설계를 하게 되었습니다.

이를 보완하고자 불필요한 양방향 관계매핑을 줄이고 Embeded type을 사용하여 관계매핑을 재설계하여 ERD를 최적화 시켜보았습니다.

- <a href="https://mason-lee.tistory.com/160" target='_blank'> [ERD 최적화 블로그 링크] </a>

<br/>

# 🤔 문제 발생 및 해결
- 윈도우 운영체제에서 Linux 쉘 스크립트 파일 작성시 발생한 에러 <a href="https://mason-lee.tistory.com/156" target='_blank'> [블로그 링크] </a>
- ThreadPoolTaskExecutor를 이용한 스레드 풀 설정 <a href="https://mason-lee.tistory.com/155" target='_blank'> [블로그 링크] </a>
- TCP 3-way handshake 단계에서 Spring Websocket의 Session에 Principal 정보 삽입 <a href="https://mason-lee.tistory.com/154" target='_blank'> [블로그 링크] </a>

<br/>




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

<br/>

