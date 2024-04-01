package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CoordinateTest {
    @ParameterizedTest
    @CsvSource("1,1", "13,6", "4,7")
    fun `좌표 객체는 좌표 값을 가지고 있다`(
        row: Int,
        col: Int,
    ) {
        val coordinate = Coordinate(Row(row), Column(col))

        assertThat(coordinate.row.value).isEqualTo(row)
        assertThat(coordinate.col.value).isEqualTo(col)
    }
}
