package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PointTest {

    @Test
    fun `같은 좌표값을 가지고 있는 Point 객체는 서로 동일하다`(){
        assertThat(Point('A', 1)).isEqualTo(Point('A', 1))
    }

    @Test
    fun `x좌표에 값을 더해서 새로운 Point를 반환한다`() {
        val point = Point('A', 2)
        val newPoint = point.addX(2)
        assertThat(newPoint).isEqualTo(Point('C', 2))
    }

    @Test
    fun `y좌표에 값을 더해서 새로운 Point를 반환한다`() {
        val point = Point('A', 10)
        val newPoint = point.addY(-2)
        assertThat(newPoint).isEqualTo(Point('A', 8))
    }
}