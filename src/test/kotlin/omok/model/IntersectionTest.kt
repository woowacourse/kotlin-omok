package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class IntersectionTest {
    @Test
    fun `백돌은 위치를 가진다`() {
        val actual = Intersection(Position.of(1, 2), IntersectionState.WHITE).position

        val expected = Position.of(1, 2)

        assertThat(actual).isEqualTo(expected)
    }
}
