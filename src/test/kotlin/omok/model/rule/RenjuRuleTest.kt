package omok.model.rule

import omok.model.BlackTurn
import omok.model.Board
import omok.model.Point
import omok.model.Stone
import omok.model.StoneType
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
        board.putStone(Stone(StoneType.BLACK, Point(3, 11)))
        board.putStone(Stone(StoneType.BLACK, Point(4, 13)))
        board.putStone(Stone(StoneType.BLACK, Point(4, 12)))
        board.putStone(Stone(StoneType.BLACK, Point(5, 11)))

        val turn = BlackTurn().putStone(Point(4, 11), board)
        assertThat(turn is BlackTurn).isTrue()
    }

    @Test
    fun `떨어져있는 '3-3'일 경우 착수가 금지된다`() {
        board.putStone(Stone(StoneType.BLACK, Point(2, 5)))
        board.putStone(Stone(StoneType.BLACK, Point(3, 4)))
        board.putStone(Stone(StoneType.BLACK, Point(5, 5)))
        board.putStone(Stone(StoneType.BLACK, Point(5, 4)))

        val turn = BlackTurn().putStone(Point(5, 2), board)
        assertThat(turn is BlackTurn).isTrue()
    }

    @Test
    fun `장목일 경우 착수가 금지된다`() {
        board.putStone(Stone(StoneType.BLACK, Point(2, 8)))
        board.putStone(Stone(StoneType.BLACK, Point(2, 7)))
        board.putStone(Stone(StoneType.BLACK, Point(2, 6)))
        board.putStone(Stone(StoneType.BLACK, Point(2, 4)))
        board.putStone(Stone(StoneType.BLACK, Point(2, 3)))

        val turn = BlackTurn().putStone(Point(2, 5), board)
        assertThat(turn is BlackTurn).isTrue()
    }

    @Test
    fun `붙어있는 '4-4' 일 경우 착수가 금지된다`() {
        board.putStone(Stone(StoneType.BLACK, Point(3, 7)))
        board.putStone(Stone(StoneType.BLACK, Point(4, 7)))
        board.putStone(Stone(StoneType.BLACK, Point(7, 7)))
        board.putStone(Stone(StoneType.BLACK, Point(9, 7)))
        board.putStone(Stone(StoneType.BLACK, Point(10, 7)))

        val turn = BlackTurn().putStone(Point(6, 7), board)
        assertThat(turn is BlackTurn).isTrue()
    }

    @Test
    fun `떨어져있는 '4-4'일 경우 착수가 금지된다`() {
        board.putStone(Stone(StoneType.BLACK, Point(3, 12)))
        board.putStone(Stone(StoneType.BLACK, Point(4, 12)))
        board.putStone(Stone(StoneType.BLACK, Point(7, 12)))
        board.putStone(Stone(StoneType.BLACK, Point(9, 12)))
        board.putStone(Stone(StoneType.BLACK, Point(10, 12)))

        val turn = BlackTurn().putStone(Point(6, 12), board)
        assertThat(turn is BlackTurn).isTrue()
    }
}
