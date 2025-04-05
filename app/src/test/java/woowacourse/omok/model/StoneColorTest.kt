package omok.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.StoneColor.Companion.next

class StoneColorTest {
    @Test
    fun `흰색일 때 검은색을 반환한다`() {
        val black = StoneColor.BLACK

        val actual = black.next()
        val expected = StoneColor.WHITE

        assertEquals(expected, actual)
    }

    @Test
    fun `검은색일 때 흰색을 반환한다`() {
        val white = StoneColor.WHITE

        val actual = white.next()
        val expected = StoneColor.BLACK

        assertEquals(expected, actual)
    }
}
