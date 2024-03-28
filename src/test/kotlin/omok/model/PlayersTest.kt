package omok.model

import omok.model.rule.ContinualStonesStandard
import omok.model.rule.ForbiddenRules
import omok.model.rule.RuleAdapter
import omok.model.rule.library.FourFourRule
import omok.model.rule.library.OverlineRule2
import omok.model.rule.library.ThreeThreeRule
import omok.model.rule.winning.ContinualStonesWinningCondition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayersTest {
    private val blackStonePlayer =
        Player(
            Stone.BLACK,
            RuleAdapter(
                ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
                ForbiddenRules(
                    ThreeThreeRule.forBlack(),
                    FourFourRule.forBlack(),
                    OverlineRule2(),
                ),
            ),
        )

    private val whiteStonePlayer =
        Player(
            Stone.WHITE,
            RuleAdapter(
                ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.CAN_OVERLINE),
                ForbiddenRules(),
            ),
        )

    private val players = Players(blackStonePlayer, whiteStonePlayer)

    @Test
    fun `첫번째 순서의 플레이어는 검정 돌 플레이어다`() {
        val actual = players.firstOrderedPlayer()
        assertThat(actual).isEqualTo(blackStonePlayer)
    }

    @Test
    fun `검정 돌 플레이어의 다음 순서는 흰 돌 플레이어이다`() {
        val recentPlayer = blackStonePlayer

        val actual = players.nextOrder(recentPlayer)
        assertThat(actual).isEqualTo(whiteStonePlayer)
    }

    @Test
    fun `흰 돌 플레이어의 다음 순서는 검정 돌 플레이어이다`() {
        val recentPlayer = whiteStonePlayer

        val actual = players.nextOrder(recentPlayer)
        assertThat(actual).isEqualTo(blackStonePlayer)
    }
}
