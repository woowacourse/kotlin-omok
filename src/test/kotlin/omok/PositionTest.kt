package omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun `위치는 행을 가진다`() {
        val actual = Position(1, 2).row

        val expected = 1

        assertThat(actual).isEqualTo(expected)
    }
}
