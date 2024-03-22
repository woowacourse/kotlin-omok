# 1차 리뷰 후 수정 사항

- [ ] : domain 로직들 테스트코드 모두 작성
## Board
- [ ] : Board - sumLeft, sumRight 공통 로직 개선
- [x] : Board 의 크기를 size 를 넘어가면 못 놓게 리팩토링
- [x] : 오목판에 돌을 놓으면 새로운 오목판이 만들어진다 - testCase 추가
## OmokGame
- [ ] : OmokGame

## PutEvent
- [ ] : PutEvent 변경에 유연한 구조로 개선
- [ ] : PutEvent - reverse() 로직 수정
> 이 부분도 마찬가지로 reverse() 라는 함수가 여러곳에 파편화 되어있으면,
> 추후에 요구사항이 변경됐을 때 관리가 너무 어려울 것 같아요.
> 돌이 바뀌는 로직을 한곳으로 모아서 관리해볼 수 있을까요?

## StoneColor
- [ ] : StoneColor - reverse() 개선
> 돌이 순차적으로 바뀐다는 규칙은 다른 도메인에서도 반복해서 사용될 것 같은데요.
> StoneColor 에서 돌을 바꾼다는 규칙을 정의하는게 적절할까요?
> 적절하다면 StoneColor 의 코드를 중심으로 돌이 교체되는 로직들이 구현되어야합니다.

## Rule
- [ ] : PutRule - 네이밍 좀 더 구체적으로 변경
- [ ] : rule - 개별 sealed class 별로 파일을 분리

## GameState

- [ ] : GameState - 개별 sealed class 별로 파일을 분리
- [ ] : GameState - winner 개선
> winner 는 GameState 를 구현하는 모든 구현체가 알게되는 상태인데요,
> Running 에서는 사용할 일이 없습니다. 적절한 인터페이스 구조인지 고민해보면 좋을 것 같아요.
- [ ] : WhiteTurn, BlackTurn 공통 로직 빼기
- [ ] : White 와 Black 각각 rule 을 만들어주셨으므로, 생성 시점에 스스로 초기화 시켜줘도 괜찮겠습니다 :)

## View
- [ ] : input 과 outpust 을 구분
- [ ] : input 은 내부적으로 어떻게 쓰일지 아직 알 수 없으므로 model 과의 의존성을 끊도록 구현해보면 어떨까요?
- [ ] : View 함수화하기
