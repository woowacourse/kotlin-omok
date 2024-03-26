package omok.model.rule

import omok.model.ContinualStonesCondition
import omok.model.rule.ban.DoubleFourForbiddenPlace
import omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import omok.model.rule.ban.ForbiddenPlaces
import omok.model.rule.ban.OverlineForbiddenPlace2
import omok.model.rule.winning.ContinualStonesWinningCondition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GamePlayingRulesTest {
    @Test
    fun `플레이어가 가지는 게임 룰을 만든다`() {
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
        val overlineForbiddenPlace2 =
            gamePlayingRules.list.find { it is OverlineForbiddenPlace2 } as? OverlineForbiddenPlace2

        // then
        assertThat(overlineForbiddenPlace2?.continualStonesStandard).isEqualTo(ContinualStonesStandard(5))
    }
}
