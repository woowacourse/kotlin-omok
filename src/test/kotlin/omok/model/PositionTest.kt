package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class PositionTest {
    @ParameterizedTest
    @ValueSource(ints = [-1, 15, 17])
    fun `Row의 범위를 벗어날 수 없다`(row: Int) {
        assertThrows<IllegalArgumentException> { Position(Row(row), Col(1)) }
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 15, 18])
    fun `Col의 범위를 벗어날 수 없다`(col: Int) {
        assertThrows<IllegalArgumentException> { Position(Row(1), Col(col)) }
    }

    @ParameterizedTest
    @CsvSource(
        "H8, 7, 7",
        "A1, 0, 0",
        "O15, 14, 14",
    )
    fun `사용자로부터 돌의 좌표 텍스트로 포지션을 생성할 수 있다`(
        coordinateText: String,
        rowIndex: Int,
        colIndex: Int,
    ) {
        val userPosition = Position(coordinateText)
        val expected = Position(Row(rowIndex), Col(colIndex))

        assertThat(userPosition).isEqualTo(expected)
    }
}
