# kotlin-omok

## 기능 목록

- OmokGrid
    - [x] 15x15 크기를 가진 보드를 생성한다.
    - [x] 좌표와 돌 상태를 받으면 해당 위치에 돌을 놓는다.
        - [x] 좌표에 이미 돌이 있으면 예외를 던진다.


- omokGame
    - [x] 현재 돌이 오목인지 확인한다.


- 입력
    - [x] 돌을 놓을 위치를 입력 받는다.


- 출력
    - [x] 게임 시작 메시지를 출력한다.
    - [x] 보드의 상태를 출력한다.

## TODO
- [x] **OmokGrid** | getStone() 파라미터명 수정
- [x] **OmokGrid** | 내부에서만 사용하는 값 가시성 변경자 변경
- [x] 비어있는 생성자 제거
- [x] **InputView** | 매직넘버 상수화
- [x] **OutputView** | 가독성 개선 (함수 순서)
- [x] InputView | 입력 위치 판단 로직 이동
- [x] 확장 함수 위치 이동
- [x] **OmokRuleAdapter** & **Referee** | 렌주룰 의존성 제거 고민
- [ ] **Referee** | 금수 처리 고민

- [x] 콘솔용 코드 복구
- [x] OmokGame | 콘솔 위에서도 돌아가도록 수정
- [x] OmokGrid | Stones를 꺼내서 하지 말고 스스로 일하도록 바꿔 보기
- [x] OmokGrid | 테스트를 더 꼼꼼히 작성하기
- [x] ValidationResult | 에러 문자열 관리 어디서 할지 고민
- [x] MainActivity | printWinner 함수 네이밍 수정하기
- [x] MainActivity | checkGameOver 함수가 하나의 일만 하도록 수정
- [x] DbHelper | 가시성과 위치 수정하기

- [x] OutputView | 상수 고민
- [x] OmokGrid | 스스로 일하도록 바꿔 보기
- [x] Stone | Stone 사용하는 부분 변수명 바꿔 주기
- [x] RenjuRuleAdapterImpl | toList() 지우기
- [x] MainActivity | Toast 재사용하기
