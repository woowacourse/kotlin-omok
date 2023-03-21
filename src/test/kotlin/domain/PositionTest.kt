package domain

import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class PositionTest {
    @CsvSource(value = ["-1,10", "5,15"])
    @ParameterizedTest
    fun `Position의 x, y는 0~14 범위 밖이면 에러가 발생한다`(y: Int, x: Int) {
        assertThrows<IllegalArgumentException> { Position(y, x) }.shouldHaveMessage("0~14 사이의 값만 가능합니다.")
    }
}
