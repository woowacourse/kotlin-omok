package omok.model.rule

import omok.model.rule.library.FourFourRule
import omok.model.rule.library.OverlineRule2
import omok.model.rule.library.ThreeThreeRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ForbiddenRulesTest {
    @ParameterizedTest
    @ValueSource(ints = [4, 5, 6])
    fun `연속 돌 기준에 따라 장목 규칙의 카운트가 변경됨`(count: Int) {
        // given
        val forbiddenRules =
            ForbiddenRules(
                ThreeThreeRule(1, 2),
                FourFourRule(1, 2),
                OverlineRule2(),
            )

        // when
        forbiddenRules.initOverlineStandard(ContinualStonesStandard(count))
        val actual = (forbiddenRules.rules.find { it is OverlineRule2 } as OverlineRule2).count

        // then
        assertThat(actual).isEqualTo(count)
    }

    @Test
    fun `어떠한 금수 규칙을 가지고 있지 않다`() {
        val forbiddenRules = ForbiddenRules()

        assertThat(forbiddenRules.hasNoRule()).isTrue
    }

    @Test
    fun `더블 금수 규칙(3-3 혹은 4-4)을 가지고 있다`() {
        val forbiddenRules =
            ForbiddenRules(
                ThreeThreeRule(1, 2),
                FourFourRule(1, 2),
            )

        assertThat(forbiddenRules.hasDoubleRule()).isTrue
    }

    @Test
    fun `장목 금수 규칙을 가지고 있다`() {
        val forbiddenRules =
            ForbiddenRules(
                OverlineRule2(),
            )

        assertThat(forbiddenRules.hasOverlineRule()).isTrue
    }
}
