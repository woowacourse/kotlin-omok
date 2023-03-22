package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class OmokPointTest {
    @Test
    fun `좌표를 만들 수 있다`() {
        val omokPoint = OmokPoint(1, 1)
        assertAll(
            { assertThat(omokPoint.x).isEqualTo(1) },
            { assertThat(omokPoint.y).isEqualTo(1) }
        )
    }
}
