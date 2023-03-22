package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {

    @Test
    fun `좌표가 오목판의 크기를 벗어난다면 좌표는 오목판에 포함되어 있지 않은 것이다`() {
        val point = Point(Board.SIZE + 1, 1)

        assertThat(point in Board).isFalse
    }

    @Test
    fun `좌표가 오목판의 크기를 벗어나지 않는다면 좌표는 오목판에 포함되어 있는 것이다`() {
        val point = Point(Board.SIZE, Board.SIZE)

        assertThat(point in Board).isTrue
    }
}
