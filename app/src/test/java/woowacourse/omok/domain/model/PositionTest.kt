package woowacourse.omok.domain.model

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.domain.omok.model.Position

class PositionTest {
    @ParameterizedTest
    @CsvSource(
        "1,A",
        "15,O",
        "1,O",
        "15,A",
    )
    fun `보드 범위 안에 포지션을 설정할 수 있어야 한다`(
        row: Int,
        col: Char,
    ) {
        assertDoesNotThrow {
            Position.of(row, col)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "0,A",
        "16,O",
        "1,Z",
        "15,P",
    )
    fun `보드 범위를 벗어나는 좌표를 입력하면 예외를 발생시킬 수 있어야 한다`(
        row: Int,
        col: Char,
    ) {
        assertThrows<IllegalArgumentException> {
            Position.of(row, col)
        }
    }
}
