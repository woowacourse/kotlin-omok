package omok.model

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PointTest {
    @ParameterizedTest
    @ValueSource(ints = [-5, 280])
    fun `Point 의 위치가 1 이상 15 이하가 아니라면 예외가 발생한다`(pointValue: Int) {
        assertThrows<IllegalArgumentException> {
            Point(1, pointValue)
        }

        assertThrows<IllegalArgumentException> {
            Point(pointValue, 1)
        }
    }
}
