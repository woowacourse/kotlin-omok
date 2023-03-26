package omok.model.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class GoStoneColorTest {
    @ParameterizedTest
    @CsvSource("BLACK, WHITE", "WHITE, BLACK", ", BLACK")
    fun `다음 턴의 색을 가져온다`(color: GoStoneColor?, expected: GoStoneColor) {
        val nextColor = GoStoneColor.getNextColor(color)
        assertThat(nextColor).isEqualTo(expected)
    }
}
