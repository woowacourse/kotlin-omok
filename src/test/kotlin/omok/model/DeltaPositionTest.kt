package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DeltaPositionTest {
    @Test
    fun `돌의 위치 값이 반전되는지 확인한다`() {
        // given
        val position = DeltaPosition(1, 1)

        // when
        val actual = -position
        val expected = DeltaPosition(-1, -1)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
