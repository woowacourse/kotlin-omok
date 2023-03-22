package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StoneColorTest {
    @CsvSource("WHITE, BLACK", "BLACK, WHITE")
    @ParameterizedTest
    fun `다음 차례를 반환한다`(myStoneColor: StoneColor, expected: StoneColor) {
        val actual = myStoneColor.next()
        assertThat(actual).isEqualTo(expected)
    }
}
