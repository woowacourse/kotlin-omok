package domain

import domain.domain.Position
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class PositionTest {

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14])
    fun `Position은 0~14사이 x,y 좌표값을 갖는다`(coordinate: Int) {
        assertDoesNotThrow { Position(coordinate, coordinate) }
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, -2, 15, 16])
    fun `Position은 0~14가 아닌  x,y 좌표값을 갖을수 없다`(coordinate: Int) {
        assertThrows<IllegalArgumentException> { Position(coordinate, coordinate) }
    }
}
