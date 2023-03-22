package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PointTest {

    @Test
    fun `같은 좌표값을 가지고 있는 Point 객체는 서로 동일하다`() {
        assertThat(Point(0, 1)).isEqualTo(Point(0, 1))
    }

    @Test
    fun `뷰 기준으로 좌표가 주어졌을 때 절대적 위치로 변환된 Point 객체를 반환한다`() {
        assertThat(Point.create('A', 1)).isEqualTo(Point(0, 0))
    }
}