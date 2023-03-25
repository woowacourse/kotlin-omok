// import domain.judgement.RenjuRule
// import domain.stone.Color
// import domain.stone.Position
// import domain.stone.Stone
// import domain.turn.RunningBoardState
// import domain.turn.Turn
// import org.assertj.core.api.Assertions.assertThat
// import org.junit.jupiter.api.Test
// import org.junit.jupiter.api.assertAll
// import org.junit.jupiter.api.assertThrows
//
// class TurnTest {
//     @Test
//     fun `다음 턴과 일치하지 않습니다`() {
//         assertAll(
//             "보드의 상태와 턴의 색상에 논리적인 오류가 존재해서 예외가 발행한다.",
//             {
//                 // 흰 색부터 턴이 시작되는 경우
//                 assertThrows<IllegalStateException> {
//                     Turn(
//                         Color.WHITE,
//                         RunningBoardState(
//                             RenjuRule(), listOf<Stone>().convertToBoard(), Stone(
//                                 Position(1, 3), Color.BLACK
//                             )
//                         )
//                     )
//                 }
//             },
//             {
//                 // 마지막 놓인 돌이 검은색인데 이번 턴을 검은색에게 줬을 때
//                 val latestStone = Stone(1, 3, Color.BLACK)
//                 assertThrows<IllegalStateException> {
//                     Turn(
//                         Color.BLACK,
//                         RunningBoardState(
//                             RenjuRule(),
//                             listOf(latestStone).convertToBoard(),
//                             latestStone
//                         )
//                     )
//                 }
//             }
//         )
//     }
//
//     @Test
//     fun `이미 놓여진 돌에 돌을 넣으려고 시도하면 자신을 반환한다`() {
//         val stone = Stone(1, 3, Color.BLACK)
//         val turn = Turn(
//             Color.WHITE,
//             RunningBoardState(RenjuRule(), listOf(stone).convertToBoard(), stone)
//         )
//         val nextTurn = turn.putStone(stone.position)
//         assertThat(nextTurn).isSameAs(turn)
//     }
// }
