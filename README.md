# kotlin-omok

## OmokGame

### 📝 Business Logic Specification
- [x] 오목 게임을 플레이 할 수 있다
- [x] 승리조건: 오목 이상의 장목을 먼저 만든다
  - [x] 연속으로 연결된 5개 이상의 수가 있는지 확인한다
- [x] 서로 번갈아가면서 돌을 놓는다(흑, 백)
- [x] 흑과 백이 둔 수는 저장되어야한다



---
## 🏛 Class Design

### Axis
- [x] 행값이 적절한지 판단한다 (A ~ O)
- [x] 열 값이 적절한지 판단한다 (0 ~ 15)


### Stone
- [x] 좌표 값 하나를 담는다
- [x] Color 를 가진다


### Stones
- [x] Stone 의 리스트를 담는다
- [x] Stone을 추가할 수 있다
- [x] 마지막 Stone을 반환할 수 있다


### Player
- [x] Stones를 담고 있다
- [x] Stone의 색깔을 담는다.


### BlackStonePlayer
- [x] 흑색 상태를 담고 있다
- [x] Stone 을 더할 수 있다
- [x] 마지막 Stone 을 알 수 있다
- [x] 렌주룰이 적용된다


### WhiteStonePlayer
- [x] 백색 상태를 담고 있다


### Board
- [x] 모든 Stone 을 저장한다
- [x] 중복된 Stone 이 들어올 경우 예외를 던진다


## OutputView
- [x] 오목 게임을 시작하는 안내 메세지를 출력할 수 있다
- [x] 15x15 판을 출력할 수 있다
- [x] 사용자가 지정한 위치에 바둑을 출력할 수 있다 (○, ●)
- [x] 우승자를 출력할 수 있다


## InputView
- [x] 바둑을 놓을 위치를 입력받는다
  - [x] A ~ O 사이의 열 값을 받는다
  - [x] 1 ~ 15 사이의 행 값을 받는다
  - [x] 위 행과 열을 합쳐서 두글자로 입력을 받는다