package omok.model

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AxisTest {
    @ParameterizedTest
    @ValueSource(ints = [0, 16, -5, 280])
    fun `열의 위치가 A ~ O 사이가 아니라면 예외가 발생한다`(col: Int) {
        assertThrows<IllegalArgumentException> {
            Axis("A", col)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["adsklfalkdf", "Z", "😀", "", "a", "AB"])
    fun `행의 위치가 1 ~ 15 사이가 아니라면 예외가 발생한다`(row: String) {
        assertThrows<IllegalArgumentException> {
            Axis(row, 1)
        }
    }
}
