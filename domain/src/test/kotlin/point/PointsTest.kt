package point

import domain.point.Point
import domain.point.Points
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class PointsTest {
    @Test
    fun `각각 다른 위치를 가질 수 있다`() {
        assertDoesNotThrow {
            Points(Point(1, 1), Point(1, 2))
        }
    }

    @Test
    fun `중복되는 위치의 위치를 가지면 에러가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Points(Point(1, 1), Point(1, 1))
        }
    }

    @Test
    fun `새로 위치를 추가할 수 있다`() {
        val points = Points(Point(1, 1), Point(1, 2))
        val acutal = points.add(Point(1, 3))

        assertThat(acutal).isEqualTo(Points(Point(1, 1), Point(1, 2), Point(1, 3)))
    }

    @Test
    fun `이미 놓은 위치인지 판단한다`() {
        val points = Points(Point(1, 1), Point(1, 2), Point(1, 3))
        val expected = points.hasStone(Point(1, 1))

        assertThat(expected).isTrue
    }

    @Test
    fun `마지막 위치를 반환한다`() {
        val points = Points(Point(1, 1), Point(1, 2), Point(1, 3))
        val expected = points.last

        assertThat(expected).isEqualTo(Point(1, 3))
    }

    @Test
    fun `마지막 놓은 위치가 없으면 null을 반환한다`() {
        val points = Points()
        val expected = points.last

        assertThat(expected).isNull()
    }

    @Test
    fun `위치 목록을 반환한다`() {
        val points = Points(Point(1, 1), Point(1, 2), Point(1, 3))
        val expected = points.getAll()
        val actual = listOf(Point(1, 1), Point(1, 2), Point(1, 3))

        assertThat(expected).isEqualTo(actual)
    }
}
