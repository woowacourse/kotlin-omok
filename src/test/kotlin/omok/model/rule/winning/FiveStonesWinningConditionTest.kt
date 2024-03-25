package omok.model.rule.winning

import omok.model.Position
import omok.model.Stone
import omok.model.StonePosition
import omok.model.initBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FiveStonesWinningConditionTest {
    private val playerStone = Stone.BLACK
    private val winningCondition = FiveStonesWinningCondition()

    @Test
    fun `오목이 되면 승리한다`() {
        // given
        val board =
            initBoard(
                StonePosition(Position(3, 3), playerStone),
                StonePosition(Position(3, 4), playerStone),
                StonePosition(Position(3, 5), playerStone),
                StonePosition(Position(3, 6), playerStone),
                StonePosition(Position(3, 7), playerStone),
            )

        // when
        val actual = winningCondition.isWin(board, Position(3, 7))

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `오목이 안 되면 승리하지 않는다`() {
        // given
        val board =
            initBoard(
                StonePosition(Position(3, 3), playerStone),
                StonePosition(Position(4, 3), playerStone),
                StonePosition(Position(5, 3), playerStone),
                StonePosition(Position(6, 3), playerStone),
                StonePosition(Position(1, 3), playerStone),
            )

        // when
        val actual = winningCondition.isWin(board, Position(1, 3))

        // then
        assertThat(actual).isFalse
    }
}
