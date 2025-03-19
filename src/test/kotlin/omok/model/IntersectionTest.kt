package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rule.wrapper.point.Point

class IntersectionTest {
    @Test
    fun `백돌은 위치를 가진다`() {
        val actual = Intersection(Point(1, 2), IntersectionState.WHITE).point

        val expected = Point(1, 2)

        assertThat(actual).isEqualTo(expected)
    }
}
