# 오목왕이 되어보자

## View

- InputView
    -[ ] 좌표를 입력받는다.

- OutputView
    - [ ] 바둑판을 그린다.
    - [ ] 누구 차례인지 알려준다.
    - [ ] 마지막 돌의 위치를 알려준다.
    - [ ] 승패를 알려준다.

## Domain

- Coordinate
    -[x] 0~14의 범위를 갖는다.
    -[x] 캐싱해서 사용한다(발급한다).
- Position
    -[x] x좌표를 갖는다.
    -[x] y좌표를 갖는다.
- Stone
    -[x] Position을 갖는다.
    -[x] Stone을 구현한 WhiteStone이 존재한다.
    -[x] Stone을 구현한 BlackStone이 존재한다.
- Stones(Board)
    -[ ] blackStones가 있다. (List)
    -[ ] whiteStones가 있다. (List)
    -[ ] last stone position을 반환한다.
    -[ ] 돌을 놓는다.
        - [ ] 이미 돌이 있는 자리에 놓을 수 없다.
        - [ ] 검정 돌을 놓는다.
        - [ ] 흰 돌을 놓는다.
    -[x] 차례를 반환한다.
- Rule (interface)
    - 승리 조건
        -[ ] 5이상이 되면 승리
    - 흑돌 금지 조건
        -[ ] 3-3
        -[ ] 4-4
        -[ ] 4-3-3
        -[ ] 4-4-3
        -[ ] 장목
        -[ ] 예외: 위 사항들에 해당되면서도 5가 만들어지면 승리

