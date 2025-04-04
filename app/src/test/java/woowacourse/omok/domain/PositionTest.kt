package woowacourse.omok.domain

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PositionTest {
    @ValueSource(ints = [-1, 15])
    @ParameterizedTest
    fun `위치는 0보다 작거나 오목판의 크기보다 큰 위치를 가질 수 없다`(int: Int) {
        assertThrows<IllegalArgumentException> { Position(int, int) }
    }
}
