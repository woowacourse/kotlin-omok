package omok.model

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PositionTest {
    @ParameterizedTest
    @ValueSource(strings = ["Z1", "0C", "C0", "AA", "CC", "Z"])
    fun `유효하지 않은 오목판 위치에 착수하면 예외 발생`(input: String) {
        assertThatThrownBy {
            Position.from(input)
        }.isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Position.ERROR_INVALID_POSITION)
    }

    @Test
    fun `입력값이 없으면 예외 발생`() {
        assertThatThrownBy {
            Position.from("")
        }.isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Position.ERROR_EMPTY_INPUT)
    }

    @ParameterizedTest
    @ValueSource(strings = ["A1", "O15", "A6", "C8", "D3", "H10"])
    fun `유효한 위치라면 착수할 수 있다`(input: String) {
        assertDoesNotThrow {
            Position.from(input)
        }
    }
}
