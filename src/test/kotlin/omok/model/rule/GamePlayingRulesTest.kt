package omok.model.rule

import omok.model.ContinualStonesCondition
import omok.model.Position
import omok.model.Stone
import omok.model.initBoard
import omok.model.rule.ban.DoubleFourForbiddenPlace
import omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import omok.model.rule.ban.ForbiddenPlaces
import omok.model.rule.ban.OverlineForbiddenPlace
import omok.model.rule.winning.ContinualStonesWinningCondition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GamePlayingRulesTest {
    @Test
    fun `사목으로 지정할 경우 더블 규칙을 가질 수 없다`() {
        assertThrows<IllegalArgumentException> {
            GamePlayingRules.from(
                ContinualStonesWinningCondition(ContinualStonesStandard(4), ContinualStonesCondition.EXACT),
                ForbiddenPlaces(
                    DoubleOpenThreeForbiddenPlace(Stone.BLACK),
                    DoubleFourForbiddenPlace(Stone.BLACK),
                    OverlineForbiddenPlace(),
                ),
            )
        }
    }

    @Test
    fun `우승 조건이 N 목 이상일 경우 장목 규칙을 가질 수 없다`() {
        assertThrows<IllegalArgumentException> {
            GamePlayingRules.from(
                ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.CAN_OVERLINE),
                ForbiddenPlaces(
                    DoubleOpenThreeForbiddenPlace(Stone.BLACK),
                    DoubleFourForbiddenPlace(Stone.BLACK),
                    OverlineForbiddenPlace(),
                ),
            )
        }
    }

    @Test
    fun `규칙을 가지고 있지 않으면 비어있는 위치에 모든 돌을 둘 수 있다`() {
        val board = initBoard()
        val gamePlayingRules =
            GamePlayingRules.from(
                ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
                ForbiddenPlaces(),
            )
        val actual = gamePlayingRules.availablePosition(board, Position(3, 3))
        assertThat(actual).isTrue
    }
}
