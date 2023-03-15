// import org.assertj.core.api.Assertions.assertThat
// import org.junit.jupiter.api.Test
// import org.junit.jupiter.api.assertAll
//
// class BlackStonesTest {
//     @Test
//     fun `돌을 추가할 수 있다`() {
//         val blackStones = BlackStones()
//         val nextStones = blackStones.putStone(Stone(1, 1))
//         val expected = BlackStones(Stone(1, 1))
//
//         assertAll(
//             "돌을 추가할 수 있다",
//             { assertThat(nextStones).isInstanceOf(BlackStones::class.java) },
//             { assertThat(nextStones.list).isEqualTo(expected.list) }
//         )
//     }
//
//     @Test
//     fun `관리하고 있는 돌의 색깔을 알 수 있다`() {
//         val blackStones = BlackStones()
//         val actual = blackStones.getColor()
//         val expected = Color.BLACK
//
//         assertThat(actual).isEqualTo(expected)
//     }
// }
