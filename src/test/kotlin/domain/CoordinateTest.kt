package domain

import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class CoordinateTest {
    @ValueSource(ints = [-1, 15])
    @ParameterizedTest
    fun `0에서 14가 아닌 좌표가 들어오면 오류를 발생시킨다`(num: Int) {
        assertThrows<IllegalArgumentException> { Coordinate(num) }.shouldHaveMessage("잘못된 좌표값입니다. 현재 입력된 좌표: $num")
    }

    @ValueSource(ints = [0, 14])
    @ParameterizedTest
    fun `좌표는 0에서 14여야만 한다`(num: Int) {
        assertDoesNotThrow { Coordinate(num) }
    }
}
