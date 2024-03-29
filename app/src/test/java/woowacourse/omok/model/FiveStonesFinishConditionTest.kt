package woowacourse.omok.model

import omok.model.board.Position
import omok.model.board.Stone
import omok.model.game.FinishType
import omok.model.player.Player
import omok.model.rule.finish.FiveStonesFinishCondition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FiveStonesFinishConditionTest {
    private val player = Player(Stone.BLACK)
    private val playerStone = Stone.BLACK
    private val winningCondition = FiveStonesFinishCondition()

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
        val actual = winningCondition.finishType(board, Position(3, 7), player)

        // then
        assertThat(actual).isEqualTo(FinishType.BLACK_PLAYER_WIN)
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
        val actual = winningCondition.finishType(board, Position(1, 3), player)

        // then
        assertThat(actual).isEqualTo(FinishType.NOT_FINISH)
    }
}
