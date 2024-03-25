package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun `위로 이동한다`() {
        // given
        val position = Position(7, 7)

        // when
        val actual = position.move(Direction.UP)

        // then
        assertThat(actual).isEqualTo(Position(6, 7))
    }

    @Test
    fun `아래로 이동한다`() {
        // given
        val position = Position(7, 7)

        // when
        val actual = position.move(Direction.DOWN)

        // then
        assertThat(actual).isEqualTo(Position(8, 7))
    }

    @Test
    fun `왼쪽으로 이동한다`() {
        // given
        val position = Position(7, 7)

        // when
        val actual = position.move(Direction.LEFT)

        // then
        assertThat(actual).isEqualTo(Position(7, 6))
    }

    @Test
    fun `오른쪽으로 이동한다`() {
        // given
        val position = Position(7, 7)

        // when
        val actual = position.move(Direction.RIGHT)

        // then
        assertThat(actual).isEqualTo(Position(7, 8))
    }
}
