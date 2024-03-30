# 오목 ⚫️

---
## Step 1, 2

### Domain

- [x] 승리 조건을 판단한다.
- [x] 위치가 유효한지 판단한다.
    - [x] 이미 놓여진 자리인지 확인한다.
    - [x] 위치가 A1 ~ O15 안에 있는지 확인한다.
- [x] 렌주룰에 적용되는지 판단한다.
- [x] 게임을 진행한다.

### InputView

- [x] 위치를 입력한다.
- [x] 차례를 알려준다.
- [x] 마지막 돌의 위치를 알려준다.

### OutputView

- [x] 게임 시작 문구를 출력한다.
- [x] 오목 판을 출력한다.
- [x] 돌을 표시한다.

---

## Step 3

### Android

- [X] 진행 중인 게임이 있다면 이전 기록을 불러온다.
- [X] 돌을 둔다.
- [X] 오목인지 판단한다.
- [X] 금수인지 판단한다.
- [X] 오목이라면 더 이상 수를 둘 수 없다.
- [X] 오목이라면 우승자를 알려준다.
- [X] 오목일 때 사용자가 다시 시작(Snackbar Action)을 누르면 게임을 재시작한다.
  - [X] 보드를 초기화한다.
  - [X] 돌을 검정돌로 초기화한다.

---

## Step 4

### 데이터베이스

- [X] 돌의 위치를 저장한다.
- [X] 모든 돌의 정보를 가져온다.
- [ ] 모든 돌의 정보를 삭제한다.
