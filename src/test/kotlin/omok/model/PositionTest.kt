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
}
