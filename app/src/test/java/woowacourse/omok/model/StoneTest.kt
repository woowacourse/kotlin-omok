package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StoneTest {
    @ParameterizedTest
    @CsvSource("1,1", "13,6", "4,7")
    fun `돌은 위치와 색상을 가지고 있다`(
        x: Int,
        y: Int,
    ) {
        val coordinate = Coordinate(x, y)
        val stone = Stone(Color.BLACK, coordinate)

        assertThat(stone.color.name).isEqualTo("BLACK")
        assertThat(stone.coordinate).isEqualTo(coordinate)
    }
}
