package omok

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StoneTest {

    @ParameterizedTest
    @CsvSource(value = ["WHITE,BLACK", "WHITE,BLACK"])
    fun `흑색 돌에 reverse하면 백색 돌이 된다`(stone: Stone, expect: Stone) {
        val actual = stone.reverse()
        assertThat(actual).isEqualTo(expect)
    }
}
