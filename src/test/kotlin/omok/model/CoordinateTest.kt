package omok.model

import omok.model.stone.Coordinate
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CoordinateTest {
    @Test
    fun `알파벳+숫자 형태의 입력을 뒤집어서 위치를 생성한다`() {
        val coordinate = Coordinate("H11")

        assertAll(
            { assertThat(coordinate.x).isEqualTo(5) },
            { assertThat(coordinate.y).isEqualTo(8) }
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["33", "aa", "4a", "H-1", "H17", "z8", "d7d"])
    fun `알파벳+숫자 형태가 아닌 입력은 오류가 발생한다`(input: String) {
        assertThatIllegalArgumentException()
            .isThrownBy { Coordinate(input) }
            .withMessageContaining("위치를 알파벳숫자 형태로 입력해주세요. (잘못된 값: $input")
    }
}
