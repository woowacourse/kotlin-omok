package omok.model.rule

import omok.model.board.Board
import omok.model.position.Col
import omok.model.position.Position
import omok.model.position.Row
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

        blackStone.putStone(Position(Row('C'), Col.from(12)))
        blackStone.putStone(Position(Row('E'), Col.from(12)))
        blackStone.putStone(Position(Row('D'), Col.from(13)))
        blackStone.putStone(Position(Row('D'), Col.from(14)))

        val actual = DoubleThreeChecker.isDoubleThree(Position(Row('D'), Col.from(12)))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 금수 케이스 2`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(Row('B'), Col.from(6)))
        blackStone.putStone(Position(Row('C'), Col.from(5)))
        blackStone.putStone(Position(Row('E'), Col.from(5)))
        blackStone.putStone(Position(Row('E'), Col.from(6)))

        val actual = DoubleThreeChecker.isDoubleThree(Position(Row('E'), Col.from(3)))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 금수 케이스 3`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(Row('J'), Col.from(9)))
        blackStone.putStone(Position(Row('N'), Col.from(9)))
        blackStone.putStone(Position(Row('M'), Col.from(10)))
        blackStone.putStone(Position(Row('M'), Col.from(12)))

        val actual = DoubleThreeChecker.isDoubleThree(Position(Row('L'), Col.from(11)))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 금수 케이스 4`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(Row('K'), Col.from(3)))
        blackStone.putStone(Position(Row('K'), Col.from(6)))
        blackStone.putStone(Position(Row('M'), Col.from(4)))
        blackStone.putStone(Position(Row('N'), Col.from(4)))

        val actual = DoubleThreeChecker.isDoubleThree(Position(Row('K'), Col.from(4)))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `열린 3, 닫힌 3은 3-3 금수가 아니다`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(Row('C'), Col.from(14)))
        blackStone.putStone(Position(Row('C'), Col.from(13)))
        blackStone.putStone(Position(Row('B'), Col.from(12)))
        blackStone.putStone(Position(Row('A'), Col.from(12)))

        val actual = DoubleThreeChecker.isDoubleThree(Position(Row('C'), Col.from(11)))
        assertThat(actual).isEqualTo(false)
    }
}
