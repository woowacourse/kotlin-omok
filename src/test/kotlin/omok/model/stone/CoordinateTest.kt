package omok.model.stone

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CoordinateTest {
    @Test
    fun `알파벳+숫자 형태의 입력으로 알파벳은 순서에 맞게끔 숫자로 변환하여 위치를 생성한다`() {
        val coordinate = Coordinate.createWithMark("H11")

        assertAll(
            { assertThat(coordinate.x).isEqualTo(8) },
            { assertThat(coordinate.y).isEqualTo(11) }
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["33", "aa", "4a", "H-1", "d7d"])
    fun `알파벳+숫자 형태가 아닌 입력은 오류가 발생한다`(input: String) {
        assertThatIllegalArgumentException()
            .isThrownBy { Coordinate.createWithMark(input) }
            .withMessageContaining("위치를 알파벳숫자 형태로 입력해주세요. (잘못된 값: $input")
    }
}
