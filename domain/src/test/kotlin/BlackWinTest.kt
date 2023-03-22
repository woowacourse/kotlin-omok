// import domain.judgement.FiveStoneWinningCondition
// import domain.judgement.RenjuRuleForbiddenCondition
// import domain.stone.Color
// import org.junit.jupiter.api.Test
// import org.junit.jupiter.api.assertThrows
// import java.lang.IllegalStateException
//
// class BlackWinTest {
//     @Test
//     fun `검은 돌이 이기지 않았으면 예외가 발생한다`() {
//         val latestStone = Stone(1, 7, Color.BLACK)
//         val stones = listOf(
//             Stone(1, 1, Color.BLACK),
//             Stone(2, 1, Color.WHITE),
//             Stone(1, 2, Color.BLACK),
//             Stone(2, 2, Color.WHITE),
//             Stone(1, 3, Color.BLACK),
//             Stone(2, 3, Color.WHITE),
//             Stone(1, 4, Color.BLACK),
//             Stone(2, 4, Color.WHITE),
//         )
//         val nextBoard = PlacedBoard((stones + latestStone).convertToBoard())
//
//         assertThrows<IllegalStateException> {
//             BlackWin(
//                 nextBoard,
//                 PlacedBoard(stones.convertToBoard()),
//                 latestStone,
//                 FiveStoneWinningCondition(),
//                 RenjuRuleForbiddenCondition()
//             )
//         }
//     }
// }
