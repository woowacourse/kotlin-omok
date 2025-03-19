package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class XTest {
    @ParameterizedTest
    @ValueSource(strings = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"])
    fun `X좌표는 A부터 O까지 좌표를 가진다`(point: String) {
        assertDoesNotThrow {
            X(point)
        }
    }

    @ParameterizedTest
    @CsvSource(value = ["A,1", "B,2", "C,3", "D,4", "E,5", "F,6", "G,7", "H,8", "I,9", "J,10", "K,11", "L,12", "M,13", "N,14", "O,15"])
    fun `x좌표를 알파벳에 맞는 숫자로 치환한다`(
        alphaBet: String,
        number: Int,
    ) {
        val x = X(alphaBet).toNumber()
        assertThat(x).isEqualTo(number)
    }
}
