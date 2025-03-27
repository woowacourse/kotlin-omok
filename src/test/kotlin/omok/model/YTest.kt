package omok.model

import omok.model.board.Y
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class YTest {
    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15])
    fun `Y좌표는 1부터 15까지 좌표를 가진다`(value: Int) {
        assertDoesNotThrow {
            Y(value)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 16])
    fun `x좌표는 1부터 15사이의 점 외에 다른 값이 들어오면 에러를 발생한다`(value: Int) {
        assertThrows<IllegalArgumentException> {
            Y(value)
        }
    }
}
