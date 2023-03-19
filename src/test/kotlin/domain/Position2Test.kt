package domain

import domain.domain.Position2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Position2Test {

    @Test
    fun `위치는 x좌표값을 갖는다`() {
        // given
        val x = 1
        val y = 2
        // when
        val position = Position2(x, y)
        val actual = position.x
        val expected = 1
        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `위치는 y좌표값을 갖는다`() {
        // given
        val x = 1
        val y = 2
        // when
        val position = Position2(x, y)
        val actual = position.y
        val expected = 2
        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오른쪽으로 한칸,위쪽으로 두칸 옮기면 x 좌표가 1,y 좌표가 2 증가한다`() {
        // given
        val x = 1
        val y = 2
        val position = Position2(x, y)
        // when
        val actual = position.move(1, 2)
        // then
        val expected = Position2(2, 4)
        assertThat(actual).isEqualTo(expected)
    }
}
