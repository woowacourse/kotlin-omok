package woowacourse.omok.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CooridnateTest {
    @Test
    fun `보드를 벗어난 입력은 예외`() {
        assertThrows<IllegalArgumentException> {
            Coordinate(100, 100)
        }
    }
}
