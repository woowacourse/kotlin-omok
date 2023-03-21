package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import rule.wrapper.point.Point

class BoardTest {

    @Test
    fun `board에 스톤을 추가 할 수 있다`() {
        val board = Board()
        board.putStone(Stone(Point(1, 1), StoneType.BLACK))
        board.putStone(Stone(Point(3, 1), StoneType.WHITE))

        assertAll(
            { assertThat(board.board[1][1]).isEqualTo(StoneType.BLACK) },
            { assertThat(board.board[3][1]).isEqualTo(StoneType.WHITE) },
            { assertThat(board.stones.values.size).isEqualTo(2) },
        )
    }

    @Test
    fun `스톤들의 위치들을 가지고 있다`() {
        val board = Board()
        board.putStone(Stone(Point(1, 1), StoneType.BLACK))
        board.putStone(Stone(Point(3, 1), StoneType.WHITE))
        board.putStone(Stone(Point(2, 1), StoneType.BLACK))
        board.putStone(Stone(Point(4, 1), StoneType.WHITE))
        board.putStone(Stone(Point(2, 7), StoneType.BLACK))
        board.putStone(Stone(Point(3, 8), StoneType.WHITE))
        board.putStone(Stone(Point(2, 12), StoneType.BLACK))
        board.putStone(Stone(Point(3, 10), StoneType.WHITE))

        assertThat(board.whiteStonesPoint()).hasSize(4)
    }

    @Test
    fun `white 스톤들의 위치들을 가지고 있다`() {
        val board = Board()
        board.putStone(Stone(Point(1, 1), StoneType.BLACK))
        board.putStone(Stone(Point(3, 1), StoneType.WHITE))
        board.putStone(Stone(Point(2, 1), StoneType.BLACK))
        board.putStone(Stone(Point(4, 1), StoneType.WHITE))
        board.putStone(Stone(Point(2, 7), StoneType.BLACK))
        board.putStone(Stone(Point(3, 8), StoneType.WHITE))
        board.putStone(Stone(Point(2, 12), StoneType.BLACK))
        board.putStone(Stone(Point(3, 10), StoneType.WHITE))

        assertAll({
            assertThat(board.whiteStonesPoint()).hasSize(4)
            assertThat(board.blackStonesPoint()).hasSize(4)
        })
    }
}
