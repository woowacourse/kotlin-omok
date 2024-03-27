package omok.model

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PositionTest {
    @ParameterizedTest
    @CsvSource(
        "1,1",
        "15,15",
        "1,15",
        "15,1",
    )
    fun `보드 범위 안에 포지션을 설정할 수 있다`(
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
    ) {
        assertDoesNotThrow {
            Position(horizontalCoordinate, verticalCoordinate)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "0,1",
        "16,16",
        "1,27",
        "15,19",
    )
    fun `보드 범위를 벗어나는 좌표를 입력하면 예외를 발생시킨다`(
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
    ) {
        assertThrows<IllegalArgumentException> {
            Position(horizontalCoordinate, verticalCoordinate)
        }
    }
}
