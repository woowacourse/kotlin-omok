package omok.model.board

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PositionTest {
    @Test
    fun `가로 좌표는 1부터 15 사이의 값 이어야 한다`() {
        assertThrows<IllegalArgumentException> { Position(16, 15) }
    }

    @Test
    fun `세로 좌표는 1부터 15 사이의 값 이어야 한다`() {
        assertThrows<IllegalArgumentException> { Position(15, 0) }
    }
}
