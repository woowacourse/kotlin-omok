package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PositionTest {
    @ParameterizedTest
    @CsvSource(
        value = [
            "0,15,1", "14,15,15",
        ],
    )
    fun `평면화된 바둑판 위치를 제공하면, 올바른 행과 열 정보를 제공한다`(
        flattenedIndex: Int,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
    ) {
        val position = Position(flattenedIndex)
        assertThat(position).isEqualTo(Position(horizontalCoordinate, verticalCoordinate))
    }
}
