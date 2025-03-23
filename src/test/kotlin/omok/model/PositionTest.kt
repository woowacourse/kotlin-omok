package omok.model

import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import org.assertj.core.api.Assertions.assertThat
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
}
