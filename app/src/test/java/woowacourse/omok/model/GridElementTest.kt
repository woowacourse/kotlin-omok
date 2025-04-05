package woowacourse.omok.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.model.position.GridElement

class GridElementTest {
    @Test
    fun `0 이상의 값이 아닐 경우 예외를 발생시킨다`() {
        assertThrows<IllegalArgumentException> { GridElement(-1) }
    }
}
