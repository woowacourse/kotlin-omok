package omok.model.rule

import omok.model.board.Board
import omok.model.position.Col
import omok.model.position.Position
import omok.model.position.Row
import omok.model.stone.BlackStone
import omok.model.stone.Stone
import omok.model.stone.WhiteStone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FourFourCheckerTest {
    @BeforeEach
    fun setUp() {
        repeat(Board.BOARD_SIZE) { row ->
            repeat(Board.BOARD_SIZE) { col ->
                Board.board[row][col] = Stone.NONE
            }
        }
    }

    @Test
    fun `4-4 케이스`() {
        val blackStone = BlackStone()
        val whiteStone = WhiteStone()
        blackStone.putStone(Position(Row('C'), Col.from(10)))
        blackStone.putStone(Position(Row('C'), Col.from(11)))
        blackStone.putStone(Position(Row('C'), Col.from(12)))
        blackStone.putStone(Position(Row('C'), Col.from(14)))
        blackStone.putStone(Position(Row('C'), Col.from(15)))
        blackStone.putStone(Position(Row('D'), Col.from(12)))
        blackStone.putStone(Position(Row('G'), Col.from(12)))
        blackStone.putStone(Position(Row('I'), Col.from(12)))
        blackStone.putStone(Position(Row('J'), Col.from(12)))
        blackStone.putStone(Position(Row('E'), Col.from(5)))
        blackStone.putStone(Position(Row('E'), Col.from(6)))
        blackStone.putStone(Position(Row('F'), Col.from(5)))
        blackStone.putStone(Position(Row('G'), Col.from(5)))
        blackStone.putStone(Position(Row('G'), Col.from(4)))
        blackStone.putStone(Position(Row('H'), Col.from(6)))
        blackStone.putStone(Position(Row('H'), Col.from(7)))
        blackStone.putStone(Position(Row('H'), Col.from(8)))
        blackStone.putStone(Position(Row('J'), Col.from(8)))
        blackStone.putStone(Position(Row('K'), Col.from(8)))
        blackStone.putStone(Position(Row('J'), Col.from(9)))
        blackStone.putStone(Position(Row('J'), Col.from(6)))

        whiteStone.putStone(Position(Row('D'), Col.from(5)))
        whiteStone.putStone(Position(Row('H'), Col.from(9)))

        val actual1 = DoubleFourChecker.isDoubleFour(Position(Row('C'), Col.from(8)))
        assertThat(actual1).isEqualTo(true)

        val actual2 = DoubleFourChecker.isDoubleFour(Position(Row('F'), Col.from(12)))
        assertThat(actual2).isEqualTo(true)

        val actual3 = DoubleFourChecker.isDoubleFour(Position(Row('H'), Col.from(5)))
        assertThat(actual3).isEqualTo(true)

        val actual4 = DoubleFourChecker.isDoubleFour(Position(Row('I'), Col.from(8)))
        assertThat(actual4).isEqualTo(true)

        val actual5 = DoubleFourChecker.isDoubleFour(Position(Row('J'), Col.from(10)))
        assertThat(actual5).isEqualTo(true)
    }
}
