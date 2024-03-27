package omok.model.position

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ColumnTest {
    @Test
    fun `플레이어가 유효하지 않은 Column 값을 입력한다면 예외가 발생한다`() {
        assertThatThrownBy { Column.from(16) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("유효하지 않은 y축 입니다. 현재 입력 값: 16\n")
    }
}
