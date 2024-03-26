package omok.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class CoordinateTest {
    @ParameterizedTest
    @CsvSource("-1, 0", "0, -1", "0, 16", "18, 5")
    fun `유효하지 않은 오목판 위치에 착수하면 예외 발생`(
        x: Int,
        y: Int,
    ) {
        assertThrows<IllegalArgumentException> {
            Coordinate(x, y)
        }
    }

    @Test
    fun `입력값이 없으면 예외 발생`() {
        assertThrows<IllegalArgumentException> {
            stringToCoordinate("")
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["A1", "O15", "A6", "C8", "D3", "H10"])
    fun `유효한 위치라면 착수할 수 있다`(input: String) {
        assertDoesNotThrow {
            stringToCoordinate(input)
        }
    }

    private fun stringToCoordinate(input: String): Coordinate {
        require(input.isNotEmpty()) { "입력값이 필요합니다." }

        val inputX = input.first().uppercaseChar() - 'A'
        val inputY = input.substring(1).toIntOrNull()?.minus(1) ?: throw IllegalArgumentException("유효하지 않은 위치입니다.")
        return Coordinate(inputX, inputY)
    }
}
