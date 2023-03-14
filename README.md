# kotlin-omok

## Domain

### OmokGame

- [ ] 게임 진행
    - [ ] 흑백 반복하며 게임을 진행한다

### Board

- [x] BlackTurn, WhiteTurn을 갖는다
- [ ] 둘 수 있는지 확인한다
    - [x] 빈공간 확인

### State (일급컬렉션)

- [x] 15x15 2차원 리스트를 갖는다
- [x] 착수한다
- [x] 둘 수 있는지 확인한다

### Turn (추상클래스)

- [x] state를 갖는다
- [x] 착수한다
- [x] 둘 수 있는지 확인한다

### BlackTurn

- [ ] 3-3 확인
- [ ] 4-4 확인
- [ ] 장목 확인

### WhiteTurn

### Referee

- [ ] 게임 승패 판단

### Stone

- [x] x, y 를 갖는다
- [x] 보드 범위 내 확인

## View

### InputView

- [ ] 좌표 입력 받기

### OutputView

- [ ] 게임 시작 문구 출력
- [ ] 오목판 출력
