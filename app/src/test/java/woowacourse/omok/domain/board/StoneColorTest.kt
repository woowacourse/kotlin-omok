package woowacourse.omok.domain.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneColorTest {
    @Test
    fun `WHITE일 때 BLACK을 반환한다`() {
        val state = StoneColor.WHITE

        val actual = state.reverseStoneColor()
        val expected = StoneColor.BLACK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `BLACK일 때 WHITE을 반환한다`() {
        val state = StoneColor.BLACK

        val actual = state.reverseStoneColor()
        val expected = StoneColor.WHITE

        assertThat(actual).isEqualTo(expected)
    }
}
