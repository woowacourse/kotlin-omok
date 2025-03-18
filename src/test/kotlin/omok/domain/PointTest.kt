package omok.domain

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PointTest {
    @ParameterizedTest
    @ValueSource(ints = [0, 1, 10, 14])
    fun `x좌표는 0에서 14 사이이다`(number: Int) {
        assertDoesNotThrow {
            Point(number, 0)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 10, 14])
    fun `y좌표는 0에서 14 사이이다`(number: Int) {
        assertDoesNotThrow {
            Point(0, number)
        }
    }
}
