package omok.model

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class XTest {
    @ParameterizedTest
    @ValueSource(strings = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"])
    fun `X좌표는 A부터 O까지 좌표를 가진다`(point: String) {
        assertDoesNotThrow {
            X(point)
        }
    }
}
