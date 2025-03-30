package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.domain.Board
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.PositionOccupiedException
import woowacourse.omok.domain.StoneType
import woowacourse.omok.domain.TurnViolationException

class BoardTest {
    @Test
    fun lastTurn() {
        val board = Board.initial()
        assertThat(board.lastTurn).isEqualTo(StoneType.EMPTY)
    }

    @Test
    fun put() {
        val board = Board.initial()
        board.put(Position(0, 0), StoneType.BLACK)
        assertThat(board.lastTurn).isEqualTo(StoneType.BLACK)
    }

    @Test
    fun notYourTurnException() {
        val board = Board.initial()
        board.put(Position(0, 0), StoneType.BLACK)
        assertThrows<TurnViolationException> {
            board.put(Position(0, 0), StoneType.BLACK)
        }
    }

    @Test
    fun duplicatePutException() {
        val board = Board.initial()
        board.put(Position(0, 0), StoneType.BLACK)
        assertThrows<PositionOccupiedException> {
            board.put(Position(0, 0), StoneType.WHITE)
        }
    }
}
