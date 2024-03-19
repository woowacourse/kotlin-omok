package omok.model

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun `돌의 위치가 유효하지 않을 경우 예외처리한다`() {
        assertThatThrownBy { Position(15, 15) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("유효하지 않은 위치입니다. 현재 입력 값: 15, 15")
    }
}
