## Board

1) size 에 해당하는 빈 Board 생성
2) x, y, boxState 에 해당하는 Boxes 들이 들어올 때 Board 생성
3) place Stone rule 설정
    - Renju Rule
    - Empty 면 못 놓음

- forbiddenBlocks : 놓을 수 없는 블록들

## Blocks
- blocks : Map<Position, boxState>
- fun toList(): List<List<boxState>>
- fun hasOmok(): 오목이 있는지 확인한다.

# Block
x, y, boxState

## BlockState
Black, White, Empty, Forbidden, OmokStone
