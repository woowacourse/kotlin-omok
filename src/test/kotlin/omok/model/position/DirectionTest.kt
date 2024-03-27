package omok.model.position

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DirectionTest {
    @Test
    fun `돌의 위치 값에 -를 붙이면 Row와 Column 값이 반전된다`() {
        // given
        val position = Direction(Row(1), Column(1))

        // when
        val actual = -position
        val expected = Direction(Row(-1), Column(-1))

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
