package omok.model

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PositionTest {
    @CsvSource("-1, 0", "16, 0", "0, -1")
    @ParameterizedTest
    fun `위치는 행과 열이 0~14 사이가 아니면 예외가 발생한다`(
        row: Int,
        col: Int,
    ) {
        assertThrows<IllegalArgumentException> { Position(row, col) }
    }

    @CsvSource("0, 0", "0, 14", "3, 5")
    @ParameterizedTest
    fun `위치는 행과 열이 모두 0~14 사이여야 한다`(
        row: Int,
        col: Int,
    ) {
        assertDoesNotThrow { Position(row, col) }
    }
}
