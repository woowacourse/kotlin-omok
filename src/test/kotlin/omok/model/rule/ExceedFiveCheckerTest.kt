package omok.model.rule

import X_C
import Y_10
import Y_11
import Y_12
import Y_13
import Y_14
import Y_15
import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.Stone
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExceedFiveCheckerTest {
    @BeforeEach
    fun setUp() {
        repeat(Board.BOARD_SIZE) { row ->
            repeat(Board.BOARD_SIZE) { col ->
                Board.board[row][col] = Stone.NONE
            }
        }
    }

    @Test
    fun `장목 케이스`() {
        val blackStone = BlackStone()
        blackStone.putStone(Position(X_C, Y_10))
        blackStone.putStone(Position(X_C, Y_11))
        blackStone.putStone(Position(X_C, Y_12))
        blackStone.putStone(Position(X_C, Y_14))
        blackStone.putStone(Position(X_C, Y_15))

        val actual = ExceedFiveChecker.isMoreThanFive(Position(X_C, Y_13))
        Assertions.assertThat(actual).isEqualTo(true)
    }
}
