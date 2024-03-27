# 1차 리뷰 후 수정 사항

- [x] : domain 로직들 테스트코드 모두 작성

## Board
- [x] : Board - sumLeft, sumRight 공통 로직 개선
- [x] : Board 의 크기를 size 를 넘어가면 못 놓게 리팩토링
- [x] : 오목판에 돌을 놓으면 새로운 오목판이 만들어진다 - testCase 추가
- [x] : Board 의 좌표에 해당하는 곳에 이미 돌이 있으면 돌을 놓을 수 없다 - testCase 추가

## Vector
- [x] : Vector 에 4방향 vectors 추가

## BoardSize
- [x] : BoardSize 는 1 이상이여야함

## OmokGame
- [x] : OmokGame while 문 변경

## GameEvent (as is : PutEvent)
- [x] : GameEvent 변경에 유연한 구조로 개선 (의문점 있음 대기..)
- [x] : GameEvent - reverse() 로직 삭제

조금 더 고려해볼 부분 : GameEnd event 를 받아서 꼭 처리를 해야할까...?
controller 에서 게임 끝나고 결과 받아서 end 이벤트 처리하면 될 거 같은데 🤔

## StoneColor
- [x] : StoneColor 삭제

## Rule
- [x] : PutRule - 네이밍 좀 더 구체적으로 변경
- [x] : rule - 개별 class 별로 파일을 분리
- [x] : WhiteCanPutRule 삭제 및 리팩토링
## GameState

- [x] : GameState - 개별 sealed class 별로 파일을 분리
- [x] : GameState - winner 삭제
- [x] : WhiteTurn, BlackTurn 공통 로직 빼기
- [x] : White 와 Black 각각 rule 을 만들어주셨으므로, 생성 시점에 스스로 초기화 시켜줘도 괜찮겠습니다 :)

- [x] : test - 오목알을 놓고, 오목이 완성되지 않으면 다음 플레이어 턴으로 넘어간다
- [x] : test - 오목알을 놓고, 오목이 완성되면 게임이 끝난다
## View
- [x] : input 과 outpust 을 구분
- [x] : uimodel 
- [x] : View 함수화하기
