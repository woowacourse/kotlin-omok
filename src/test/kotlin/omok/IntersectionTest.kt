package omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class IntersectionTest {
    @Test
    fun `백돌은 위치를 가진다`() {
        val actual = Intersection(Position(Line(1), Line(2)), IntersectionState.WHITE).position

        val expected = Position(Line(1), Line(2))

        assertThat(actual).isEqualTo(expected)
    }
}
