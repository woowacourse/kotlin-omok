package omok

import omok.model.StoneColor
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StoneColorTest {
    @ParameterizedTest
    @CsvSource(value = ["WHITE,BLACK", "WHITE,BLACK"])
    fun `흑색 돌에 reverse하면 백색 돌이 된다`(
        stoneColor: StoneColor,
        expect: StoneColor,
    ) {
        val actual = stoneColor.reverse()
        assertThat(actual).isEqualTo(expect)
    }
}
