# kotlin-omok

### 기능 목록

### Position
- [x] Column과 Row를 갖고 있다.

### Stone
- [x] 자신이 놓여져 있는 위치를 알 수 있다.
- [x] 자신의 색깔을 알 수 있다.

### PlacedBoard - 책임: 보드판의 상황을 알고 있다.
- [x] 돌을 추가할 수 있다.
- [x] 색깔 별 돌의 개수를 얻을 수 있다.
- [x] 보드판을 조회할 수 있다.

### Turn - 턴이라는 상태 표현을 위한 인터페이스
- [x] 현재 턴이 어떤 색의 턴인지 알고 있다.
- [x] 게임이 끝났는지 여부를 알 수 있다.
- [x] 그 턴에 돌을 놓으면 적당한 다음 턴을 반환한다.

### RunningTurn
- [x] 게임이 아직 끝나지 않은 상태다.
- [x] 돌을 놓을 수 있다.

### FinishedTurn
- [x] 게임이 끝난 상태다.
- [x] 돌을 추가할 수 없다. 

### BlackTurn
- [x] 검은색의 턴을 의미하는 상태이다.
- [x] 이 상태에서 돌을 놓는다면 BlackWin 혹은 WhiteWin 혹은 WhiteTurn 으로 상태가 이동 가능하다.

### WhiteTurn
- [x] 흰색의 턴을 의미하는 상태이다.
- [x] 이 상태에서 돌을 놓는다면 WhiteWin 혹은 BlackTurn 으로 상태가 이동 가능하다.
- 
### OmokGame
- [x] 게임을 시작할 수 있다.
- [x] 번갈아가면서 돌을 놓게 한다.
  - [x] 누구의 턴인지 알고 있다.
  - [x] 마지막 놓인 돌이 누구인지 추적한다.
  - [x] 한 쪽이 승리 조건을 달성하면 게임을 종료한다.
- [x] 승리자를 반환한다.

### FiveStoneWinningCondition
- [x] 승리조건을 달성한 색깔이 있는지 판단한 결과를 반환한다.

### RenjuRuleForbiddenCondition
- [x] 기존의 돌들과 이번에 새로 놓일 돌을 토대로 금수인지 판단한다.

### OmokController
- [x] OmokGame과 인풋뷰와 아웃풋뷰를 연결해서 게임을 시작해준다. 
