package omok.model.rule

import X_C
import X_D
import X_E
import X_F
import X_G
import X_H
import X_I
import X_J
import X_K
import Y_10
import Y_11
import Y_12
import Y_14
import Y_15
import Y_4
import Y_5
import Y_6
import Y_7
import Y_8
import Y_9
import omok.model.board.Board
import omok.model.position.Position
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
            repeat(Board.BOARD_SIZE) { column ->
                Board.board[row][column] = Stone.NONE
            }
        }
    }

    @Test
    fun `4-4 케이스`() {
        val blackStone = BlackStone()
        val whiteStone = WhiteStone()
        blackStone.putStone(Position(X_C, Y_10))
        blackStone.putStone(Position(X_C, Y_11))
        blackStone.putStone(Position(X_C, Y_12))
        blackStone.putStone(Position(X_C, Y_14))
        blackStone.putStone(Position(X_C, Y_15))
        blackStone.putStone(Position(X_D, Y_12))
        blackStone.putStone(Position(X_G, Y_12))
        blackStone.putStone(Position(X_I, Y_12))
        blackStone.putStone(Position(X_J, Y_12))
        blackStone.putStone(Position(X_E, Y_5))
        blackStone.putStone(Position(X_E, Y_6))
        blackStone.putStone(Position(X_F, Y_5))
        blackStone.putStone(Position(X_G, Y_5))
        blackStone.putStone(Position(X_G, Y_4))
        blackStone.putStone(Position(X_H, Y_6))
        blackStone.putStone(Position(X_H, Y_7))
        blackStone.putStone(Position(X_H, Y_8))
        blackStone.putStone(Position(X_J, Y_8))
        blackStone.putStone(Position(X_K, Y_8))
        blackStone.putStone(Position(X_J, Y_9))
        blackStone.putStone(Position(X_J, Y_6))

        whiteStone.putStone(Position(X_D, Y_5))
        whiteStone.putStone(Position(X_H, Y_9))

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
