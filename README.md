# kotlin-omok

## 기능 목록

data class Position
- [X] x좌표와 y좌표를 알고 있다.
- [X] x좌표는 1이상 15이하의 값을 가진다.
- [X] y좌표는 1이상 15이하의 값을 가진다.
---
enum StoneType
- [x] BLACK, WHITE를 가진다.
Stone
- [x] 자신의 위치와 스톤 타입을 가진다.
abstact Stones
- [x] 스톤들의 정보를 담고 있다.
- [ ] 스톤을 추가한다.
- [ ] 오목 조건을 충족하는지 확인하다.
WhiteStones
BlackStones
---
interface State   
abstract Put : State  
- [ ] 오목 조건 충족하면 End 상태로 보낸다.
PutWhite : Put
- [ ] 오목 조건 충족하면 End 상태로 White가 Win
PutBlack : Put
- [ ] 오목 조건 충족하면 End 상태로 Black이 Win
End : State
- [ ] 우승자에 해당하는 StoneType을 가진다.
- [ ] 우승자의 StoneType을 반환한다.
---
Board
- [ ] 게임 턴의 상태를 가진다.
---
InputView
- [ ] 위치를 입력받는다.
---
OutputView
- [ ] 흑돌들과 백돌들을 받아서 바둑판 위에 출력한다.
- [ ] 누구 차례인지 출력한다.
- [ ] 마지막 돌의 위치를 출력한다.
- [ ] 누가 승리했는지 출력한다.(black이면 흑 white면 백)


