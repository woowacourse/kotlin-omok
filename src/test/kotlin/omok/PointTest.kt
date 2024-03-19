package omok

import omok.fixtures.createPoint
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PointTest {
    @ParameterizedTest
    @CsvSource(value = ["1:1", "1:2", "15:15"], delimiter = ':')
    fun `Point 는 싱글톤`(
        x: Int,
        y: Int,
    ) {
        assertThat(createPoint(x, y)).isEqualTo(createPoint(x, y))
        assertThat(createPoint(x, y)).isSameAs(createPoint(x, y))
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "0:0", "0:1", "1:0",
            "15:16", "16:15", "16:16",
        ],
        delimiter = ':',
    )
    fun `"x, y 는 (0, 0) ~ (15, 15) 사이가 아니면 예외`(
        x: Int,
        y: Int,
    ) {
        assertThrows<IllegalStateException> { createPoint(x, y) }
    }
}
