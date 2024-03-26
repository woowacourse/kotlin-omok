package omok.model.rule.ban

import omok.model.rule.ContinualStonesStandard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ForbiddenPlacesTest {
    @Test
    fun `금수 규칙에 장목 금수 규칙이 있으면 연속 돌 기준에 맞춰 초기화한다`() {
        // given
        val forbiddenPlaces =
            ForbiddenPlaces(
                DoubleOpenThreeForbiddenPlace(),
                DoubleFourForbiddenPlace(),
                OverlineForbiddenPlace(),
            )

        // when
        val actualForbiddenPlaces = forbiddenPlaces.initOverlineStandard(ContinualStonesStandard(5))
        val actual =
            (actualForbiddenPlaces.list.find { it is OverlineForbiddenPlace } as? OverlineForbiddenPlace)?.continualStonesStandard

        // then
        assertThat(actual).isEqualTo(ContinualStonesStandard(5))
    }

    @Test
    fun `금수 규칙에 더블 금수 규칙(3-3, 4-4)이 있는지 확인한다`() {
        // given
        val forbiddenPlaces =
            ForbiddenPlaces(
                DoubleOpenThreeForbiddenPlace(),
                OverlineForbiddenPlace(),
            )

        // when
        val actual = forbiddenPlaces.haveDoubleRule()

        // then
        assertThat(actual).isTrue
    }
}
