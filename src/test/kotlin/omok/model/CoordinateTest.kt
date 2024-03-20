package omok

import omok.model.Column
import omok.model.Coordinate
import omok.model.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CoordinateTest {
    @ParameterizedTest
    @CsvSource("1,A", "13,F", "4,G")
    fun `좌표 객체는 좌표 값을 가지고 있다`(
        row: String,
        col: String,
    ) {
        val coordinate = Coordinate(Row.from(row), Column.from(col))

        assertThat(coordinate.row.value).isEqualTo(row.toInt())
        assertThat(coordinate.col.value).isEqualTo(col.first() - 'A' + 1)
    }
}
