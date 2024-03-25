package omok.model.rule.winning

import omok.model.ContinualStonesCondition
import omok.model.Position
import omok.model.Stone
import omok.model.StonePosition
import omok.model.initBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ContinualStonesWinningConditionTest {
    @ParameterizedTest
    @ValueSource(strings = ["BLACK", "WHITE"])
    fun `장목 비허용 & 오목 우승일 경우, 정확히 오목이 되면 승리한다`(stoneName: String) {
        // given
        val playerStone = Stone.valueOf(stoneName)
        val winningCondition =
            ContinualStonesWinningCondition(
                continualStonesStandard = 5,
                continualStonesCondition = ContinualStonesCondition.EXACT,
                ContinualStones(),
            )

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

    @ParameterizedTest
    @ValueSource(strings = ["BLACK", "WHITE"])
    fun `장목 허용 & 오목 우승일 경우, 육목이 되면 승리하지 않는다`(stoneName: String) {
        // given
        val playerStone = Stone.valueOf(stoneName)
        val winningCondition =
            ContinualStonesWinningCondition(
                continualStonesStandard = 5,
                continualStonesCondition = ContinualStonesCondition.EXACT,
                ContinualStones(),
            )

        val board =
            initBoard(
                StonePosition(Position(3, 3), playerStone),
                StonePosition(Position(3, 4), playerStone),
                StonePosition(Position(3, 5), playerStone),
                StonePosition(Position(3, 6), playerStone),
                StonePosition(Position(3, 7), playerStone),
                StonePosition(Position(3, 8), playerStone),
            )

        // when
        val actual = winningCondition.isWin(board, Position(3, 7))

        // then
        assertThat(actual).isFalse
    }
}
