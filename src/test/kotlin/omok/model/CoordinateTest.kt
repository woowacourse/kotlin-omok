package omok

import omok.model.Coordinate
import omok.model.PositionX
import omok.model.PositionY
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CoordinateTest {
    @ParameterizedTest
    @CsvSource("1,A", "13,F", "4,G")
    fun `좌표 객체는 좌표 값을 가지고 있다`(
        x: Int,
        y: String,
    ) {
        val coordinate = Coordinate(PositionX(x), PositionY.from(y))

        assertThat(coordinate.x.value).isEqualTo(x)
        assertThat(coordinate.y.value).isEqualTo(y.first() - 'A' + 1)
    }
}
