package omok.model.rule

import X_A
import X_B
import X_C
import X_D
import X_E
import X_J
import X_K
import X_L
import X_M
import X_N
import Y_10
import Y_11
import Y_12
import Y_13
import Y_14
import Y_3
import Y_4
import Y_5
import Y_6
import Y_9
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
            repeat(Board.BOARD_SIZE) { column ->
                Board.board[row][column] = Stone.NONE
            }
        }
    }

    @Test
    fun `3-3 금수 케이스 1`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(X_C, Y_12))
        blackStone.putStone(Position(X_E, Y_12))
        blackStone.putStone(Position(X_D, Y_13))
        blackStone.putStone(Position(X_D, Y_14))

        val actual = DoubleThreeChecker.isDoubleThree(Position(X_D, Y_12))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 금수 케이스 2`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(X_B, Y_6))
        blackStone.putStone(Position(X_C, Y_5))
        blackStone.putStone(Position(X_E, Y_5))
        blackStone.putStone(Position(X_E, Y_6))

        val actual = DoubleThreeChecker.isDoubleThree(Position(X_E, Y_3))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 금수 케이스 3`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(X_J, Y_9))
        blackStone.putStone(Position(X_N, Y_9))
        blackStone.putStone(Position(X_M, Y_10))
        blackStone.putStone(Position(X_M, Y_12))

        val actual = DoubleThreeChecker.isDoubleThree(Position(X_L, Y_11))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `3-3 금수 케이스 4`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(X_K, Y_3))
        blackStone.putStone(Position(X_K, Y_6))
        blackStone.putStone(Position(X_M, Y_4))
        blackStone.putStone(Position(X_N, Y_4))

        val actual = DoubleThreeChecker.isDoubleThree(Position(X_K, Y_4))
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `열린 3, 닫힌 3은 3-3 금수가 아니다`() {
        val blackStone = BlackStone()

        blackStone.putStone(Position(X_C, Y_14))
        blackStone.putStone(Position(X_C, Y_13))
        blackStone.putStone(Position(X_B, Y_12))
        blackStone.putStone(Position(X_A, Y_12))

        val actual = DoubleThreeChecker.isDoubleThree(Position(X_C, Y_11))
        assertThat(actual).isEqualTo(false)
    }
}
