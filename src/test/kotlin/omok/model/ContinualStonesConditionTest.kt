package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ContinualStonesConditionTest {
    @Test
    fun `EXACT 일 때, 오직 연속 돌의 숫자가 기준과 같으면 참이다`() {
        val exact = ContinualStonesCondition.EXACT
        val standardContinualCount = 5
        val actualContinualCount = 5
        assertThat(exact.map(actualContinualCount, standardContinualCount)).isTrue
    }

    @Test
    fun `EXACT 일 때, 오직 연속 돌의 숫자가 기준과 다르면 거짓이다`() {
        val exact = ContinualStonesCondition.EXACT
        val standardContinualCount = 5
        val actualContinualCount = 4
        assertThat(exact.map(actualContinualCount, standardContinualCount)).isFalse
    }

    @ParameterizedTest
    @ValueSource(ints = [5, 6])
    fun `CAN_OVERLINE 일 때, 연속 돌의 숫자가 기준보다 크거나 같으면 참이다`(actualContinualCount: Int) {
        val canOverline = ContinualStonesCondition.CAN_OVERLINE
        val standardContinualCount = 5
        assertThat(canOverline.map(actualContinualCount, standardContinualCount)).isTrue
    }

    @Test
    fun `CAN_OVERLINE 일 때, 연속 돌의 숫자가 기준보다 작으면 거짓이다`() {
        val canOverline = ContinualStonesCondition.CAN_OVERLINE
        val standardContinualCount = 5
        val actualContinualCount = 4
        assertThat(canOverline.map(actualContinualCount, standardContinualCount)).isFalse
    }
}
