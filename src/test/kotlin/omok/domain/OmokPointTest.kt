package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokPointTest {
    @Test
    fun `오목 좌표를 만들 수 있다`() {
        val omokPoint = OmokPoint('A', 1)
        assertThat(omokPoint.x).isEqualTo(XCoordinate('A'))
        assertThat(omokPoint.y).isEqualTo(YCoordinate(1))
    }

    @Test
    fun `오목 좌표를 숫자로 만들 수 있다`() {
        val omokPoint = OmokPoint(1, 1)
        assertThat(omokPoint.x).isEqualTo(XCoordinate(1))
        assertThat(omokPoint.y).isEqualTo(YCoordinate(1))
    }
}
