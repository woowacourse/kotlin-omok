package omok.model.position

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class RowTest {
    @Test
    fun `플레이어가 유효하지 않은 Row 값을 입력한다면 예외가 발생한다`() {
        assertThatThrownBy { Row('P') }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("유효하지 않은 x축입니다. 현재 입력 값: P\n")
    }
}
