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
    -[ ] 0~14의 범위를 갖는다.
    -[ ] 캐싱해서 사용한다(발급한다).
- Position
    -[ ] x좌표를 갖는다.
    -[ ] y좌표를 갖는다.
- Stone
    -[ ] Position을 갖는다.
    -[ ] Color를 갖는다.
- Stones(Board)
    -[ ] blackStones가 있다. (List)
    -[ ] whiteStones가 있다. (List)
    -[ ] last stone position을 반환한다.
    -[ ] 차례를 반환한다.
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

