package woowacourse.omok.model.rule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.rule.ForbiddenRules
import woowacourse.omok.domain.model.rule.library.FourFourRule
import woowacourse.omok.domain.model.rule.library.ThreeThreeRule

class ForbiddenRulesTest {
    // TODO:  erase
//    @ParameterizedTest
//    @ValueSource(ints = [4, 5, 6])
//    fun `연속 돌 기준에 따라 장목 규칙의 카운트가 변경됨`(count: Int) {
//        // given
//        val forbiddenRules =
//            ForbiddenRules(
//                ThreeThreeRule(1, 2),
//                FourFourRule(1, 2),
//                OverlineRule(),
//            )
//
//        // when
//        forbiddenRules.initOverlineStandard(ContinualStonesStandard(count))
//        val actual = (forbiddenRules.rules.find { it is OverlineRule } as OverlineRule).count
//
//        // then
//        assertThat(actual).isEqualTo(count)
//    }

//    @Test
//    fun `어떠한 금수 규칙을 가지고 있지 않다`() {
//        val forbiddenRules = ForbiddenRules()
//
//        assertThat(forbiddenRules.hasNoRule()).isTrue
//    }

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
