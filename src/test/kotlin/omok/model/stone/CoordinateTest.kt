package omok.model.stone

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CoordinateTest {
    @Test
    fun `알파벳+숫자 형태의 입력을 뒤집어서 위치를 생성한다`() {
        val coordinate = Coordinate.of("H3")

        assertAll(
            { assertThat(coordinate.x).isEqualTo(7) },
            { assertThat(coordinate.y).isEqualTo(2) }
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["33", "aa", "4a", "H-1", "H17", "z8", "d7d", "H100", "Z7"])
    fun `알파벳+숫자 형태가 아니거나 범위를 벗어난 입력은 오류가 발생한다`(input: String) {
        assertThatIllegalArgumentException()
            .isThrownBy { Coordinate.of(input) }
            .withMessageContaining("범위 내의 위치를 입력해주세요.")
    }
}
