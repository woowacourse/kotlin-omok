package omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneTest {
    @Test
    fun `돌은 위치를 가진다`() {
        val actual = Stone(Position(Line(1), Line(2))).position

        val expected = Position(Line(1), Line(2))

        assertThat(actual).isEqualTo(expected)
    }
}