package omok.model.stone.position

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PositionTest {
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

    @ParameterizedTest
    @CsvSource(
        "H, 8, 7, 7",
        "A, 1, 0, 0",
        "O, 15, 14, 14",
    )
    fun `사용자로부터 돌의 좌표 텍스트로 가로 세로 각각 받아 포지션을 생성할 수 있다`(
        coordinateColText: String,
        coordinateRowText: String,
        rowIndex: Int,
        colIndex: Int,
    ) {
        val userPosition = Position(coordinateColText, coordinateRowText)
        val expected = Position(Row(rowIndex), Col(colIndex))

        assertThat(userPosition).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        "ZZ8",
        "AA1",
        "OT15",
    )
    fun `알파벳 범위를 벗어난 좌표 텍스트로 포지션을 생성할 수 없다`(coordinateText: String) {
        assertThrows<IllegalArgumentException> { Position(coordinateText) }
    }

    @ParameterizedTest
    @CsvSource(
        "0, 7",
        "0, 0",
        "14, 14",
    )
    fun `Row와 Col 객체를 통헤 포지션을 생성할수 있다`(
        rowIndex: Int,
        colIndex: Int,
    ) {
        val userPosition = Position(Row(rowIndex), Col(colIndex))

        assertThat(userPosition.col).isEqualTo(Col(colIndex))
        assertThat(userPosition.row).isEqualTo(Row(rowIndex))
    }
}
