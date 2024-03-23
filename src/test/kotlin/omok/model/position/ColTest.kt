package omok.model.position

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ColTest {
    @Test
    fun `y축 위치가 유효하지 않을 경우 예외처리한다`() {
        assertThatThrownBy { Col.from(16) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("유효하지 않은 y축 입니다. 현재 입력 값: 16\n")
    }
}
