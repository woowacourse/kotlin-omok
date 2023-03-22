package domain.point

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PointTest {
    @Test
    fun `주어진 방향으로 한 칸 이동한다`() {
        val actual = Point(10, 10).move(1, 0)
        val expected = Point(11, 10)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `row와 col이 범위 안에 있으면 참을 반환한다`() {
        val actual = Point(5, 5).inRange(15, 15)

        assertThat(actual).isTrue
    }

    @Test
    fun `row가 범위를 벗어나면 거짓을 반환한다`() {
        val actual = Point(16, 15).inRange(15, 15)

        assertThat(actual).isFalse
    }

    @Test
    fun `col이 범위를 벗어나면 거짓을 반환한다`() {
        val actual = Point(15, 16).inRange(15, 15)

        assertThat(actual).isFalse
    }
}
