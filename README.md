# [넘블] 방문자 수 트래킹 서비스 구축하기
## 아키텍쳐
<img width="666" alt="스크린샷 2023-10-18 오후 7 30 52" src="https://github.com/alexgim961101/Tracking-Visitors/assets/74600075/6cb77d55-ac05-4a14-be60-a9771aaecddf">

### 1). notification서버를 따로 둔 이유
하나의 서버에서 계속 요청을 받으면서 지속적으로 client에 응답을 보내면 부담이 증가하기 때문

### 2). rabbitmq VS kafka
- rabbitmq에 비해 높은 처리량
- pub/sub 방식 (rabbitmq는 메세지 브로커 방식 -> notification 에서는 받는 데이터는 아주 중요한 데이터는 아니기 때문에 수신자가 확인할 필요 X)

### 3). SSE VS WebSocket
- 웹소켓은 양방향 통신이고 SSE는 단방향 통신
- 현재 서비스에서는 서버에서 client로 실시간 응답만 필요하기 때문에 SSE 사용

## 배포
<img width="671" alt="스크린샷 2023-10-18 오후 7 39 54" src="https://github.com/alexgim961101/Tracking-Visitors/assets/74600075/506bcd1e-231b-4249-b0a5-46e6c1689fd9">

### 1). 깃허브 액션 사용이유
- 간단한 사용
- 젠킨스 선택 시 서버를 한대 더 띄워야 하는데 비용 부담

### 2). 무중단 배포 방식에서 블루 그린을 사용한 이유
- 카나리 전략의 장점인 A/B 테스트는 현재 서비스에서 불필요하다고 판단
- 롤링 전략은 WAS 서버가 최소 두 대 이상이 필요



