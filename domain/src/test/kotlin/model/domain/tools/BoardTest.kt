package model.domain.tools

import model.domain.state.black.BlackTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    private val board = Board.create()

    @Test
    fun `B2 자리의 stoneColor를 확인한다`() {
        // given
        val turn = BlackTurn()
        turn.place(Location(B, TWO), board)

        // when
        val actual = board.getStone(Location(B, TWO))

        // then
        assertThat(actual).isEqualTo(Stone.BLACK)
    }

    @Test
    fun `A11 자리에 상대돌이 위치해있어, 착수가 불가능하다`() {
        // given
        val blackTurn = BlackTurn()
        blackTurn.place(Location(B, TWO), board)

        // when
        val actual = board.canPlaceStone(Location(B, TWO), Stone.WHITE)

        // then
        assertThat(actual).isFalse
    }

    companion object {
        private const val B = 1
        private const val TWO = 1
    }
}
