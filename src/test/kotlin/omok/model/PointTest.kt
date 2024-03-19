package omok.model

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PointTest {
    @ParameterizedTest
    @ValueSource(ints = [0, 16, -5, 280])
    fun `열의 위치가 A ~ O 사이가 아니라면 예외가 발생한다`(col: Int) {
        assertThrows<IllegalArgumentException> {
            Point(1, pointValue)
        }

        assertThrows<IllegalArgumentException> {
            Point(pointValue, 1)
        }
    }
}
