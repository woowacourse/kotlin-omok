package omok.model.rule

import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.Stone
import omok.model.stone.WhiteStone
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
    fun `3-3 케이스 1`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position.of('C', 12))
        blackStone.putStone(Position.of('E', 12))
        blackStone.putStone(Position.of('D', 13))
        blackStone.putStone(Position.of('D', 14))

        val actual = ThreeThreeChecker.checkThreeThree(3, 11) // D12 (3, 11)
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 케이스 2`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position.of('B', 6))
        blackStone.putStone(Position.of('C', 5))
        blackStone.putStone(Position.of('E', 5))
        blackStone.putStone(Position.of('E', 6))

        val actual = ThreeThreeChecker.checkThreeThree(4, 2) // E3 (4, 2)
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 케이스 3`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position.of('J', 9))
        blackStone.putStone(Position.of('N', 9))
        blackStone.putStone(Position.of('M', 10))
        blackStone.putStone(Position.of('M', 12))

        val actual = ThreeThreeChecker.checkThreeThree(11, 10) // L11 (11, 10)
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 케이스 4`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position.of('K', 3))
        blackStone.putStone(Position.of('K', 6))
        blackStone.putStone(Position.of('M', 4))
        blackStone.putStone(Position.of('N', 4))

        val actual = ThreeThreeChecker.checkThreeThree(10, 3) // K4 (10, 3)
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `4-4 케이스`() {
        val blackStone = BlackStone()
        val whiteStone = WhiteStone()
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

        val actual2 = ExceedFiveChecker.checkMoreThanFive(2, 12)
        assertThat(actual2).isEqualTo(true)

        val actual3 = FourFourChecker.checkFourFour(5, 11)
        assertThat(actual3).isEqualTo(true)

        val actual4 = FourFourChecker.checkFourFour(7, 4)
        assertThat(actual4).isEqualTo(true)

        val actual5 = FourFourChecker.checkFourFour(8, 7)
        assertThat(actual5).isEqualTo(true)

        val actual6 = FourFourChecker.checkFourFour(9, 9)
        assertThat(actual6).isEqualTo(true)
    }
}
