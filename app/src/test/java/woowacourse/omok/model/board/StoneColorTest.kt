package woowacourse.omok.model.board

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StoneColorTest {
    @Test
    fun `WHITE일 때 BLACK을 반환한다`() {
        val state = StoneColor.WHITE

        val actual = state.reverseStoneColor()
        val expected = StoneColor.BLACK

        assertEquals(expected, actual)
    }

    @Test
    fun `BLACK일 때 WHITE을 반환한다`() {
        val state = StoneColor.BLACK

        val actual = state.reverseStoneColor()
        val expected = StoneColor.WHITE

        assertEquals(expected, actual)
    }
}
