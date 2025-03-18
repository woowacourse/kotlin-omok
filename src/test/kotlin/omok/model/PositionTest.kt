package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun `위치는 행을 가진다`() {
        val actual = Position.of(1, 2).row.value

        val expected = 1

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `위치는 열을 가진다`() {
        val actual = Position.of(1, 2).column.value

        val expected = 2

        assertThat(actual).isEqualTo(expected)
    }
}
