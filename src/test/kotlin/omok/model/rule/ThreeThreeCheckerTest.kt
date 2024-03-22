package omok.model.rule

import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ThreeThreeCheckerTest {
    @BeforeEach
    fun setUp() {
        repeat(Board.BOARD_SIZE) { row ->
            repeat(Board.BOARD_SIZE) { col ->
                Board.board[row][col] = Stone.NONE
            }
        }
    }

    @Test
    fun `3-3 금수 케이스 1`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position.of('C', 12))
        blackStone.putStone(Position.of('E', 12))
        blackStone.putStone(Position.of('D', 13))
        blackStone.putStone(Position.of('D', 14))

        val actual = DoubleThreeChecker.isDoubleThree(Position.of('D', 12))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 금수 케이스 2`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position.of('B', 6))
        blackStone.putStone(Position.of('C', 5))
        blackStone.putStone(Position.of('E', 5))
        blackStone.putStone(Position.of('E', 6))

        val actual = DoubleThreeChecker.isDoubleThree(Position.of('E', 3))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 금수 케이스 3`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position.of('J', 9))
        blackStone.putStone(Position.of('N', 9))
        blackStone.putStone(Position.of('M', 10))
        blackStone.putStone(Position.of('M', 12))

        val actual = DoubleThreeChecker.isDoubleThree(Position.of('L', 11))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 금수 케이스 4`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position.of('K', 3))
        blackStone.putStone(Position.of('K', 6))
        blackStone.putStone(Position.of('M', 4))
        blackStone.putStone(Position.of('N', 4))

        val actual = DoubleThreeChecker.isDoubleThree(Position.of('K', 4))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `열린 3, 닫힌 3은 3-3 금수가 아니다`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position.of('C', 14))
        blackStone.putStone(Position.of('C', 13))
        blackStone.putStone(Position.of('B', 12))
        blackStone.putStone(Position.of('A', 12))

        val actual = DoubleThreeChecker.isDoubleThree(Position.of('C', 11))
        assertThat(actual).isEqualTo(false)
    }
}
