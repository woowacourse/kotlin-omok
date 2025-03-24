package omok.model.stone

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PointTest {
    @ParameterizedTest
    @ValueSource(
        ints = [-1, 0, 16],
    )
    fun `위치의 행은 1과 15 범위를 벗어나면 안된다`(row: Int) {
        assertThrows<IllegalArgumentException> {
            Point(row, 1)
        }
    }

    @ParameterizedTest
    @ValueSource(
        ints = [1, 5, 15],
    )
    fun `위치의 행은 1과 15 사이여야 한다`(row: Int) {
        assertDoesNotThrow {
            Point(row, 1)
        }
    }

    @ParameterizedTest
    @ValueSource(
        ints = [-1, 0, 16],
    )
    fun `위치의 열은 1과 15 범위를 벗어나면 안된다`(col: Int) {
        assertThrows<IllegalArgumentException> {
            Point(1, col)
        }
    }

    @ParameterizedTest
    @ValueSource(
        ints = [-1, 0, 16],
    )
    fun `위치의 열은 1과 15 사이여야 한다`(col: Int) {
        assertDoesNotThrow {
            Point(1, col)
        }
    }
}
