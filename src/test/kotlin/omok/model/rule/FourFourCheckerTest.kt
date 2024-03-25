package omok.model.rule

import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.StoneType
import omok.model.stone.WhiteStone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FourFourCheckerTest {
    @BeforeEach
    fun setUp() {
        repeat(Board.BOARD_SIZE) { row ->
            repeat(Board.BOARD_SIZE) { col ->
                Board.board[row][col] = StoneType.NONE
            }
        }
    }

    @Test
    fun `4-4 케이스`() {
        val blackStone = BlackStone
        val whiteStone = WhiteStone
        blackStone.putStone(Position.of('C', 10))
        blackStone.putStone(Position.of('C', 11))
        blackStone.putStone(Position.of('C', 12))
        blackStone.putStone(Position.of('C', 14))
        blackStone.putStone(Position.of('C', 15))
        blackStone.putStone(Position.of('D', 12))
        blackStone.putStone(Position.of('G', 12))
        blackStone.putStone(Position.of('I', 12))
        blackStone.putStone(Position.of('J', 12))
        blackStone.putStone(Position.of('E', 5))
        blackStone.putStone(Position.of('E', 6))
        blackStone.putStone(Position.of('F', 5))
        blackStone.putStone(Position.of('G', 5))
        blackStone.putStone(Position.of('G', 4))
        blackStone.putStone(Position.of('H', 6))
        blackStone.putStone(Position.of('H', 7))
        blackStone.putStone(Position.of('H', 8))
        blackStone.putStone(Position.of('J', 8))
        blackStone.putStone(Position.of('K', 8))
        blackStone.putStone(Position.of('J', 9))
        blackStone.putStone(Position.of('J', 6))

        whiteStone.putStone(Position.of('D', 5))
        whiteStone.putStone(Position.of('H', 9))

        val actual1 = FourFourChecker.checkFourFour(2, 7)
        assertThat(actual1).isEqualTo(true)

        val actual2 = FourFourChecker.checkFourFour(5, 11)
        assertThat(actual2).isEqualTo(true)

        val actual3 = FourFourChecker.checkFourFour(7, 4)
        assertThat(actual3).isEqualTo(true)

        val actual4 = FourFourChecker.checkFourFour(8, 7)
        assertThat(actual4).isEqualTo(true)

        val actual5 = FourFourChecker.checkFourFour(9, 9)
        assertThat(actual5).isEqualTo(true)
    }
}
