package domain.stone

import domain.point.Point
import domain.point.Points
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class StonesTest {
    @Test
    fun `각각 다른 위치의 오목알을 가질 수 있다`() {
        assertDoesNotThrow {
            Points(Point(1, 1), Point(1, 2))
        }
    }

    @Test
    fun `중복되는 위치의 오목알을 가지면 에러가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Points(Point(1, 1), Point(1, 1))
        }
    }

    @Test
    fun `오목알을 놓을 수 있다`() {
        val stones = Points(Point(1, 1), Point(1, 2))
        val newStones = stones.add(Point(1, 3))

        assertThat(newStones).isEqualTo(Points(Point(1, 1), Point(1, 2), Point(1, 3)))
    }

    @Test
    fun `오목알이 포함되어 있는지 판단한다`() {
        val stones = Points(Point(1, 1), Point(1, 2), Point(1, 3))
        val expected = stones.hasStone(Point(1, 1))

        assertThat(expected).isTrue
    }

    @Test
    fun `마지막 놓은 오목알을 반환한다`() {
        val points = Points(Point(1, 1), Point(1, 2), Point(1, 3))
        val expected = points.last

        assertThat(expected).isEqualTo(Point(1, 3))
    }

    @Test
    fun `마지막 놓은 오목알이 없으면 null을 반환한다`() {
        val points = Points()
        val expected = points.last

        assertThat(expected).isNull()
    }

    @Test
    fun `오목알이 놓인 위치 목록을 반환한다`() {
        val stones = Points(Point(1, 1), Point(1, 2), Point(1, 3))
        val expected = stones.getPositions()
        val actual = listOf(Position(1, 1), Position(1, 2), Position(1, 3))

        assertThat(expected).isEqualTo(actual)
    }
}
