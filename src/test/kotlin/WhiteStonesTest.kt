// import org.assertj.core.api.Assertions.assertThat
// import org.junit.jupiter.api.Test
// import org.junit.jupiter.api.assertAll
//
// class WhiteStonesTest {
//     @Test
//     fun `돌을 추가할 수 있다`() {
//         val whiteStones = WhiteStones()
//         val nextStones = whiteStones.putStone(Stone(1, 1))
//         val expected = WhiteStones(Stone(1, 1))
//
//         assertAll(
//             "돌을 추가할 수 있다",
//             { assertThat(nextStones).isInstanceOf(WhiteStones::class.java) },
//             { assertThat(nextStones.list).isEqualTo(expected.list) }
//         )
//     }
//
//     @Test
//     fun `관리하고 있는 돌의 색깔을 알 수 있다`() {
//         val whiteStones = WhiteStones()
//         val actual = whiteStones.getColor()
//         val expected = Color.WHITE
//
//         assertThat(actual).isEqualTo(expected)
//     }
// }
