package woowacourse.omok.model.rule.winning

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.ContinualStonesCondition
import woowacourse.omok.domain.model.rule.ContinualStonesStandard
import woowacourse.omok.domain.model.rule.winning.ContinualStonesWinningCondition

class ContinualStonesWinningConditionTest {
    @Test
    fun `연속 돌 기준이 사목으로 지정되어 있다면 Double 금수 규칙(3-3, 4-4) 을 지정할 수 없다`() {
        val continualStonesWinningCondition =
            ContinualStonesWinningCondition(
                ContinualStonesStandard(4),
                ContinualStonesCondition.STRICT,
            )
        assertThat(continualStonesWinningCondition.canHaveDoubleRule()).isFalse
    }
}
