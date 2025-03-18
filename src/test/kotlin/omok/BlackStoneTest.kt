package omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackStoneTest {
    @Test
    fun `흑돌은 위치를 가진다`() {
        val actual = BlackStone(Position(Line(3), Line(2))).position

        val expected = Position(Line(3), Line(2))

        assertThat(actual).isEqualTo(expected)
    }
}