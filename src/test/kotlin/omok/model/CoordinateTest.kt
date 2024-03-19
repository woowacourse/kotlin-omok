package omok

import omok.model.Coordinate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CoordinateTest {
    @ParameterizedTest
    @CsvSource("1,A", "13,F", "4,G")
    fun `좌표 객체는 좌표 값을 가지고 있다`(
        row: Int,
        col: String,
    ) {
        val coordinate = Coordinate(row, col)

        assertThat(coordinate.row).isEqualTo(row)
        assertThat(coordinate.col).isEqualTo(col)
    }

    @ParameterizedTest
    @CsvSource("16,A", "0,F", "4,P")
    fun `좌표 값이 바둑판의 범위를 벗어나면 에러를 던져야한다`(
        row: Int,
        col: String,
    ) {
        assertThrows<IllegalArgumentException> {
            Coordinate(row, col)
        }
    }
}
