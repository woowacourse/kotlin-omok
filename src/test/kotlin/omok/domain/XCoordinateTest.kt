package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class XCoordinateTest {
    @Test
    fun `x 좌표를 만들 수 있다`() {
        val xCoordinate = XCoordinate(1)
        assertThat(xCoordinate.value).isEqualTo(1)
    }
}
