package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.ContinualStonesCondition
import woowacourse.omok.domain.model.Player
import woowacourse.omok.domain.model.Players
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.rule.ContinualStonesStandard
import woowacourse.omok.domain.model.rule.ForbiddenRules
import woowacourse.omok.domain.model.rule.RuleAdapter
import woowacourse.omok.domain.model.rule.library.FourFourRule
import woowacourse.omok.domain.model.rule.library.OverlineRule
import woowacourse.omok.domain.model.rule.library.ThreeThreeRule
import woowacourse.omok.domain.model.rule.winning.ContinualStonesWinningCondition

class PlayersTest {
    private val blackStonePlayer =
        Player(
            Stone.BLACK,
            RuleAdapter(
                ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
                ForbiddenRules(
                    ThreeThreeRule.forBlack(),
                    FourFourRule.forBlack(),
                    OverlineRule(),
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
