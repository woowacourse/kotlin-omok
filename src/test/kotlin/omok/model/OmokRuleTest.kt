package omok.model

import omok.view.OutputView
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokRuleTest {
    @Test
    fun `3-3 정의 1`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(2, 11))
        blackStone.putStone(Position(4, 11))
        blackStone.putStone(Position(3, 12))
        blackStone.putStone(Position(3, 13))

        val actual1 = OmokRule(Board.board).checkRule(3, 11)
        assertThat(actual1).isEqualTo(true)
    }

    @Test
    fun `3-3 정의 2`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(1, 5))
        blackStone.putStone(Position(2, 4))
        blackStone.putStone(Position(5, 4))
        blackStone.putStone(Position(4, 4))

        val actual2 = OmokRule(Board.board).checkRule(4, 2)
        assertThat(actual2).isEqualTo(true)
    }

    @Test
    fun `3-3 정의 3`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(9, 3))
        blackStone.putStone(Position(13, 8))
        blackStone.putStone(Position(12, 9))
        blackStone.putStone(Position(12, 11))

        val actual3 = OmokRule(Board.board).checkRule(11, 10)
        assertThat(actual3).isEqualTo(true)
    }

    @Test
    fun `3-3 정의 4`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(10, 2))
        blackStone.putStone(Position(10, 5))
        blackStone.putStone(Position(12, 3))
        blackStone.putStone(Position(13, 3))

        val actual4 = OmokRule(Board.board).checkRule(10, 3)
        assertThat(actual4).isEqualTo(true)
    }

    @Test
    fun `4-4 테스트`() {
        val blackStone = BlackStone()
        val whiteStone = WhiteStone()
        blackStone.putStone(Position(2, 9))
        blackStone.putStone(Position(2, 10))
        blackStone.putStone(Position(2, 11))
        blackStone.putStone(Position(2, 13))
        blackStone.putStone(Position(2, 14))
        blackStone.putStone(Position(3, 11))
        blackStone.putStone(Position(6, 11))
        blackStone.putStone(Position(8, 11))
        blackStone.putStone(Position(9, 11))
        blackStone.putStone(Position(4, 4))
        blackStone.putStone(Position(4, 5))
        blackStone.putStone(Position(5, 4))
        blackStone.putStone(Position(6, 4))
        blackStone.putStone(Position(6, 3))
        blackStone.putStone(Position(7, 5))
        blackStone.putStone(Position(7, 6))
        blackStone.putStone(Position(7, 7))
        blackStone.putStone(Position(9, 7))
        blackStone.putStone(Position(10, 7))
        blackStone.putStone(Position(9, 8))
        blackStone.putStone(Position(9, 5))

        whiteStone.putStone(Position(3, 4))
        whiteStone.putStone(Position(7, 8))

        OutputView().drawBoard(Board)

        val actual1 = OmokRule(Board.board).checkRule(2, 7)
        assertThat(actual1).isEqualTo(true)

        val actual2 = OmokRule(Board.board).checkRule(2, 12)
        assertThat(actual2).isEqualTo(true)

        val actual3 = OmokRule(Board.board).checkRule(5, 11)
        assertThat(actual3).isEqualTo(true)

        val actual4 = OmokRule(Board.board).checkRule(7, 4)
        assertThat(actual4).isEqualTo(true)

        val actual5 = OmokRule(Board.board).checkRule(8, 7)
        assertThat(actual5).isEqualTo(true)

        val actual6 = OmokRule(Board.board).checkRule(9, 9)
        assertThat(actual6).isEqualTo(true)
    }
}
