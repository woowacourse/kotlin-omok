package woowacourse.omok.model.rule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.rule.ForbiddenRules
import woowacourse.omok.domain.model.rule.library.FourFourRule
import woowacourse.omok.domain.model.rule.library.ThreeThreeRule

class ForbiddenRulesTest {
    @Test
    fun `더블 금수 규칙(3-3 혹은 4-4)을 가지고 있다`() {
        val forbiddenRules =
            ForbiddenRules(
                ThreeThreeRule(1, 2),
                FourFourRule(1, 2),
            )

        assertThat(forbiddenRules.hasDoubleRule()).isTrue
    }
}
