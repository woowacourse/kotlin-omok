package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.domain.Board
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.PositionOccupiedException
import woowacourse.omok.domain.StoneType

class BoardTest {
    @Test
    fun `새로 생성된 보드는 비어 있다`() {
        val board = Board.initial(15)

        assertThat(board.isFull()).isFalse()
        assertThat(board.stones.all { it.color == StoneType.EMPTY }).isTrue()
    }

    @Test
    fun `보드에 돌을 놓을 수 있다`() {
        val board = Board.initial(15)
        val position = Position(1, 1)

        board.put(position, StoneType.BLACK)

        val stone = board.stones.first { it.position == position }
        assertThat(stone.color).isEqualTo(StoneType.BLACK)
    }

    @Test
    fun `이미 돌이 있는 위치에 돌을 놓으면 예외가 발생한다`() {
        val board = Board.initial(15)
        val position = Position(1, 1)
        board.put(position, StoneType.BLACK)

        assertThrows<PositionOccupiedException> {
            board.put(position, StoneType.WHITE)
        }
    }

    @Test
    fun `보드가 가득 차면 isFull이 true를 반환한다`() {
        val size = 15
        val board = Board.initial(size)

        for (x in 1..size) {
            for (y in 1..size) {
                board.put(Position(x, y), StoneType.BLACK)
            }
        }

        assertThat(board.isFull()).isTrue()
    }

    @Test
    fun `올바른 위치애 돌 배치가 되었는지 검증`() {
        val board = Board.initial()
        board.put(Position(3, 3), StoneType.BLACK)
        assertTrue(board.stones.any { it.position == Position(3, 3) })
    }
}
