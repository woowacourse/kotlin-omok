package omok.rule

import omok.model.BlackTurn
import omok.model.Board
import omok.model.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RenjuRuleTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `붙어있는 '3-3' 일 경우 착수가 금지된다`() {
        board.putStone(Point(3, 11), BlackTurn())
        board.putStone(Point(4, 13), BlackTurn())
        board.putStone(Point(4, 12), BlackTurn())
        board.putStone(Point(5, 11), BlackTurn())

        val turn = board.putStone(Point(4, 11), BlackTurn())
        assertThat(turn is BlackTurn).isTrue()
    }

    @Test
    fun `떨어져있는 '3-3'일 경우 착수가 금지된다`() {
        board.putStone(Point(2, 5), BlackTurn())
        board.putStone(Point(3, 4), BlackTurn())
        board.putStone(Point(5, 5), BlackTurn())
        board.putStone(Point(5, 4), BlackTurn())
        board.putStone(Point(5, 2), BlackTurn())

        val turn = board.putStone(Point(5, 2), BlackTurn())
        assertThat(turn is BlackTurn).isTrue()
    }

    @Test
    fun `장목일 경우 착수가 금지된다`() {
        board.putStone(Point(2, 8), BlackTurn())
        board.putStone(Point(2, 7), BlackTurn())
        board.putStone(Point(2, 6), BlackTurn())
        board.putStone(Point(2, 4), BlackTurn())
        board.putStone(Point(2, 3), BlackTurn())

        val turn = board.putStone(Point(2, 5), BlackTurn())
        assertThat(turn is BlackTurn).isTrue()
    }

    @Test
    fun `붙어있는 '4-4' 일 경우 착수가 금지된다`() {
        board.putStone(Point(3, 7), BlackTurn())
        board.putStone(Point(4, 7), BlackTurn())
        board.putStone(Point(7, 7), BlackTurn())
        board.putStone(Point(9, 7), BlackTurn())
        board.putStone(Point(10, 7), BlackTurn())

        val turn = board.putStone(Point(6, 7), BlackTurn())
        assertThat(turn is BlackTurn).isTrue()
    }

    @Test
    fun `떨어져있는 '4-4'일 경우 착수가 금지된다`() {
        board.putStone(Point(3, 12), BlackTurn())
        board.putStone(Point(4, 12), BlackTurn())
        board.putStone(Point(7, 12), BlackTurn())
        board.putStone(Point(9, 12), BlackTurn())
        board.putStone(Point(10, 12), BlackTurn())

        val turn = board.putStone(Point(6, 12), BlackTurn())
        assertThat(turn is BlackTurn).isTrue()
    }
}
