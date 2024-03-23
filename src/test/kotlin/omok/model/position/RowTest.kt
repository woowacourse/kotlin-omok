package omok.model.position

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class RowTest {
    @Test
    fun `x축 위치가 유효하지 않을 경우 예외처리한다`() {
        assertThatThrownBy { Row('P') }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("유효하지 않은 x축입니다. 현재 입력 값: P\n")
    }
}
