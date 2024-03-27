package omok.model.rule

import omok.model.board.Board
import omok.model.position.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FourFourCheckerTest {
    @BeforeEach
    fun setUp() {
        Board.reset()
    }

    @Test
    fun `4-4 케이스라면 true를 반환한다`() {
        createDoubleFour()

        val actual1 = DoubleFourChecker.isDoubleFour(Position(X_C, Y_8))
        assertThat(actual1).isEqualTo(true)

        val actual2 = DoubleFourChecker.isDoubleFour(Position(X_F, Y_12))
        assertThat(actual2).isEqualTo(true)

        val actual3 = DoubleFourChecker.isDoubleFour(Position(X_H, Y_5))
        assertThat(actual3).isEqualTo(true)

        val actual4 = DoubleFourChecker.isDoubleFour(Position(X_I, Y_8))
        assertThat(actual4).isEqualTo(true)

        val actual5 = DoubleFourChecker.isDoubleFour(Position(X_J, Y_10))
        assertThat(actual5).isEqualTo(true)
    }
}
