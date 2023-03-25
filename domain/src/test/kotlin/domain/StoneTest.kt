package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneTest {
    @Test
    fun `바둑돌은 색상과 위치를 갖는다`() {
        // given
        val position = Position(1, 1)
        val color = Color.BLACK
        // when
        val actual = Stone(color, position)
        // then
        assertThat(actual).isInstanceOf(Stone::class.java)
    }
}
