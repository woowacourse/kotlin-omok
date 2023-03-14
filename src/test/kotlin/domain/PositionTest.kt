package domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

internal class PositionTest {
    @Test
    fun `Position은 x,y 좌표값을 갖는다`() {
        val coordinateX = Coordinate(1)
        val coordinateY = Coordinate(1)
        assertDoesNotThrow { Position(coordinateX, coordinateY) }
    }
}
