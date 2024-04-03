package omok.model.rule

import omok.model.board.Board
import omok.model.position.Position
import omok.model.result.PutResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ThreeThreeCheckerTest {
    @BeforeEach
    fun setUp() {
        Board.reset()
    }

    @Test
    fun `(3-3 금수 케이스 1) 3-3 금수 자리에 돌을 놓는다면 true를 반환한다`() {
        createDoubleThree(doubleThreeForbiddenCaseOne)

        val actual = DoubleThreeChecker.check(Position(X_D, Y_12))
        assertThat(actual).isEqualTo(PutResult.DoubleThree)
    }

    @Test
    fun `(3-3 금수 케이스 2) 3-3 금수 자리에 돌을 놓는다면 true를 반환한다`() {
        createDoubleThree(doubleThreeForbiddenCaseTwo)

        val actual = DoubleThreeChecker.check(Position(X_E, Y_3))
        assertThat(actual).isEqualTo(PutResult.DoubleThree)
    }

    @Test
    fun `(3-3 금수 케이스 3) 3-3 금수 자리에 돌을 놓는다면 true를 반환한다`() {
        createDoubleThree(doubleThreeForbiddenCaseThree)

        val actual = DoubleThreeChecker.check(Position(X_L, Y_11))
        assertThat(actual).isEqualTo(PutResult.DoubleThree)
    }

    @Test
    fun `(3-3 금수 케이스 4) 3-3 금수 자리에 돌을 놓는다면 true를 반환한다`() {
        createDoubleThree(doubleThreeForbiddenCaseFour)

        val actual = DoubleThreeChecker.check(Position(X_K, Y_4))
        assertThat(actual).isEqualTo(PutResult.DoubleThree)
    }

    @Test
    fun `3-3 금수가 아닌 자리에 돌을 놓는다면 false를 반환한다(열린 3, 닫힌 3은 3-3 금수가 아니다)`() {
        createDoubleThree(doubleThreeNotForbidden)

        val actual = DoubleThreeChecker.check(Position(X_C, Y_11))
        assertThat(actual).isEqualTo(PutResult.Running)
    }
}
