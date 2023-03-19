package omok.domain

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun `해당 위치에 돌을 둘 수 있는지 판단한다`() {
        // given
        val position = Position(HorizontalAxis.H, 1)

        // when
        val placeable = position.isEmpty()

        // then
        assertThat(placeable).isTrue
    }
}
