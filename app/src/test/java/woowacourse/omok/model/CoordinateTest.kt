package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CoordinateTest {
    @ParameterizedTest
    @CsvSource("1,1", "13,6", "4,7")
    fun `좌표 객체는 좌표 값을 가지고 있다`(
        x: Int,
        y: Int,
    ) {
        val coordinate = Coordinate(x, y)

        assertThat(coordinate.x).isEqualTo(x)
        assertThat(coordinate.y).isEqualTo(y)
    }
}
