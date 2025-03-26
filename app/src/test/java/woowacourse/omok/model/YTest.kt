package omok.model

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.omok.model.board.Y

class YTest {
    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15])
    fun `Y좌표는 1부터 15까지 좌표를 가진다`(point: Int) {
        assertDoesNotThrow {
            Y(point)
        }
    }
}
