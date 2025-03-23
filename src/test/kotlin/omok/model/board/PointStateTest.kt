package omok.model.board

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PointStateTest {
    @Test
    fun `WHITE일 때 BLACK을 반환한다`() {
        val state = PointState.WHITE

        val actual = state.reverseStoneColor()
        val expected = PointState.BLACK

        assertEquals(expected, actual)
    }

    @Test
    fun `BLACK일 때 WHITE을 반환한다`() {
        val state = PointState.BLACK

        val actual = state.reverseStoneColor()
        val expected = PointState.WHITE

        assertEquals(expected, actual)
    }
}
