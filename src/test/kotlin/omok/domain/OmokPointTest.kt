package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OmokPointTest {
    @Test
    fun `좌표를 만들 수 있다`() {
        val omokPoint = OmokPoint(1, 1)
        assertThat(omokPoint.x.value).isEqualTo(1)
        assertThat(omokPoint.y.value).isEqualTo(1)
    }

    @Test
    fun `X좌표가 A-O의 범위를 벗어나면 예외가 발생한다`() {
        val exception = assertThrows<IllegalArgumentException> { OmokPoint(16, 1) }
        assertThat(exception.message).isEqualTo("X 범위를 벗어납니다.")
    }
    @Test
    fun `Y좌표가 1-15의 범위를 벗어나면 예외가 발생한다`() {
        val exception = assertThrows<IllegalArgumentException> { OmokPoint(1, 16) }
        assertThat(exception.message).isEqualTo("Y 범위를 벗어납니다.")
    }
}
