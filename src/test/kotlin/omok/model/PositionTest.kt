package omok.model

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PositionTest {
    @ParameterizedTest
    @CsvSource(
        "1,A",
        "15,O",
        "1,O",
        "15,A",
    )
    fun `보드 범위 안에 포지션을 설정할 수 있다`(
        horizontalCoordinate: Int,
        verticalCoordinate: Char,
    ) {
        assertDoesNotThrow {
            Position.of(horizontalCoordinate, verticalCoordinate)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "0,A",
        "16,O",
        "1,Z",
        "15,P",
    )
    fun `보드 범위를 벗어나는 좌표를 입력하면 예외를 발생시킨다`(
        horizontalCoordinate: Int,
        verticalCoordinate: Char,
    ) {
        assertThrows<IllegalArgumentException> {
            Position.of(horizontalCoordinate, verticalCoordinate)
        }
    }
}
