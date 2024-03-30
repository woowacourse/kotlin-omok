# kotlin-omok

## 기능 구현 목록(Step 1,2)

### 도메인
- [x] 돌을 번갈아가며 착수한다.
    - [x] 이미 돌이 있는 자리에 착수할 수 없다.
    - [x] 판을 벗어난 자리에 착수할 수 없다.
- [x] 각 플레이어의 착수 정보를 보유한다.
    - [x] 보드에서 모든 플레이어의 착수 정보를 보유한다.
- [x] 게임 종료 여부를 결정한다.
    - [x] 가로로 연속된 5개 이상의 돌을 착수한 플레이어가 승리한다.
    - [x] 세로로 연속된 5개 이상의 돌을 착수한 플레이어가 승리한다.
    - [x] 대각선으로 연속된 5개 이상의 돌을 착수한 플레이어가 승리한다.
    - [x] 위의 조건들이 만족되지 않은 상태에서 모든 칸이 꽉 채워지면 무승부이다.

### 입력
- [x] 현재 차례인 돌을 표출한다.
- [x] 마지막으로 착수된 위치를 표출한다.
- [x] 착수 위치를 입력받는다.

### 출력
- [x] 현재 착수된 현황을 보드에 표출한다.
- [x] 게임이 종료되었을 때 승자를 표출한다.

## 
Step 3, 4 추가 사항

### 도메인
- [x] (DB) 게임 진행 현황을 저장한다.
  - [x] 방을 추가하고 저장한다.
  - [x] 착수가 이뤄질 때, 현재 방의 식별자와 착수 위치, 돌의 색상에 대한 정보를 추가하고 저장한다.

### 뷰
- [x] 현재 차례인 돌을 화면에 표출한다.
- [x] 보드의 특정 위치를 클릭하면 돌을 표현하는 이미지가 추가되며 위치가 입력된다.
- [x] 마지막으로 착수된 위치를 표출한다.
- [x] 현재 착수된 현황을 화면 상 보드에 표출한다.
- [x] 게임이 종료되었을 때 승자를 토스트 메시지로 표출한다.
