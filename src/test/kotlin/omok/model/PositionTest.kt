package omok.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PositionTest {
    @Test
    fun `바둑돌의 가로 위치는 1보다 작을 수 없다`() {
        assertThrows<IllegalArgumentException> { Position(0, 1) }
    }

    @Test
    fun `바둑돌의 세로 위치는 1보다 작을 수 없다`() {
        assertThrows<IllegalArgumentException> { Position(1, 0) }
    }

    @Test
    fun `바둑돌의 가로 위치는 15보다 클 수 없다`() {
        assertThrows<IllegalArgumentException> { Position(16, 1) }
    }

    @Test
    fun `바둑돌의 세로 위치는 15보다 클 수 없다`() {
        assertThrows<IllegalArgumentException> { Position(1, 16) }
    }
}
