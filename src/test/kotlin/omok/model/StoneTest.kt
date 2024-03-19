package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StoneTest {
    @ParameterizedTest
    @CsvSource("1,A", "13,F", "4,G")
    fun `돌은 위치와 색상을 가지고 있다`(
        row: Int,
        col: String,
    ) {
        val color = "black"
        val coordinate = Coordinate(row, col)
        val stone = Stone(color, coordinate)

        assertThat(stone.color).isEqualTo(color)
        assertThat(stone.coordinate).isEqualTo(coordinate)
    }
}
