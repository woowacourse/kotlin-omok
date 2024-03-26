package omok.model.rule

import omok.model.ContinualStonesCondition
import omok.model.rule.ban.DoubleFourForbiddenPlace
import omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import omok.model.rule.ban.ForbiddenPlaces
import omok.model.rule.ban.OverlineForbiddenPlace2
import omok.model.rule.winning.ContinualStonesWinningCondition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GamePlayingRulesTest {
    @Test
    fun `플레이어가 가지는 장목 금수 규칙을 만든다`() {
        // given
        val gamePlayingRules =
            GamePlayingRules.from(
                ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
                ForbiddenPlaces(
                    DoubleOpenThreeForbiddenPlace(),
                    DoubleFourForbiddenPlace(),
                    OverlineForbiddenPlace2(),
                ),
            )

        // when
        val overlineForbiddenPlace2 = gamePlayingRules.overlineRule()

        // then
        assertThat(overlineForbiddenPlace2?.continualStonesStandard).isEqualTo(ContinualStonesStandard(5))
    }

    @Test
    fun `사목으로 지정할 경우 더블 규칙을 가질 수 없다`() {
        assertThrows<IllegalArgumentException> {
            GamePlayingRules.from(
                ContinualStonesWinningCondition(ContinualStonesStandard(4), ContinualStonesCondition.EXACT),
                ForbiddenPlaces(
                    DoubleOpenThreeForbiddenPlace(),
                    DoubleFourForbiddenPlace(),
                    OverlineForbiddenPlace2(),
                ),
            )
        }
    }
}
