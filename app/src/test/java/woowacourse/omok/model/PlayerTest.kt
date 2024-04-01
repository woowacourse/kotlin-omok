package woowacourse.omok.model

import RenjuRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerTest {
    /*
    9 [ ] [●] [ ] [ ] [ ]
    8 [ ] [ ] [ ] [ ] [ ]
    7 [ ] [●] [ ] [ ] [ ]
    6 [ ] [●] [ ] [ ] [ ]
    5 [ ] [●] [ ] [ ] [ ]
     E   F
 */
    private val blackStones =
        listOf(
            Stone(Color.BLACK, COORDINATE_F5),
            Stone(Color.BLACK, COORDINATE_F6),
            Stone(Color.BLACK, COORDINATE_F7),
            Stone(Color.BLACK, COORDINATE_F9),
        )

    private fun createCustomBoard(
        board: Board,
        stones: List<Stone>,
    ) {
        stones.forEach { stone ->
            board.putStone(stone)
        }
    }

    @Test
    fun `플레이어는 본인의 색상을 가지고 있다`() {
        // given
        val color = Color.BLACK
        val player = Player(color)

        // then
        assertThat(player.color).isEqualTo(color)
    }

    @Test
    fun `플레이어 차례가 되면 돌을 보드에 착수 한다`() {
        // given
        val stones = Stones()
        val rule = RenjuRule(stones)
        val board = Board(stones, rule)
        val player = Player(Color.BLACK)

        // when
        player.playTurn(board, COORDINATE_A1)

        // then
        assertThat(board.stones.stones.size).isEqualTo(1)
    }

    @Test
    fun `플레이어의 차례에 오목이 만들어지면 승리한다`() {
        // given
        val stones = Stones()
        val rule = RenjuRule(stones)
        val board = Board(stones, rule)
        val player = Player(Color.BLACK)
        createCustomBoard(board, blackStones)

        // when
        player.playTurn(board, COORDINATE_F8)

        // then
        assertThat(player.isWin).isTrue()
    }
}
