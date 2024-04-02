package omok.model.rule

import omok.model.board.Board
import omok.model.position.Position
import omok.model.result.PutResult
import omok.model.stone.BlackStone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ThreeThreeCheckerTest {
    @BeforeEach
    fun setUp() {
        Board.resetBoard()
    }

    @Test
    fun `3-3 케이스 1`() {
        val blackStone = BlackStone

        blackStone.putStone(Position.of('C', 12))
        blackStone.putStone(Position.of('E', 12))
        blackStone.putStone(Position.of('D', 13))
        blackStone.putStone(Position.of('D', 14))

        val actual = Board.checkRenjuRule(3, 11) // D12 (3, 11)
        assertThat(actual).isEqualTo(PutResult.DoubleThree)
    }

    @Test
    fun `3-3 케이스 2`() {
        val blackStone = BlackStone

        blackStone.putStone(Position.of('B', 6))
        blackStone.putStone(Position.of('C', 5))
        blackStone.putStone(Position.of('E', 5))
        blackStone.putStone(Position.of('E', 6))

        val actual = Board.checkRenjuRule(4, 2) // E3 (4, 2)
        assertThat(actual).isEqualTo(PutResult.DoubleThree)
    }

    @Test
    fun `3-3 케이스 3`() {
        val blackStone = BlackStone

        blackStone.putStone(Position.of('J', 9))
        blackStone.putStone(Position.of('N', 9))
        blackStone.putStone(Position.of('M', 10))
        blackStone.putStone(Position.of('M', 12))

        val actual = Board.checkRenjuRule(11, 10) // L11 (11, 10)
        assertThat(actual).isEqualTo(PutResult.DoubleThree)
    }

    @Test
    fun `3-3 케이스 4`() {
        val blackStone = BlackStone

        blackStone.putStone(Position.of('K', 3))
        blackStone.putStone(Position.of('K', 6))
        blackStone.putStone(Position.of('M', 4))
        blackStone.putStone(Position.of('N', 4))

        val actual = Board.checkRenjuRule(10, 3) // K4 (10, 3)
        assertThat(actual).isEqualTo(PutResult.DoubleThree)
    }
}
