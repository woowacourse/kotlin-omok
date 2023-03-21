package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class YCoordinateTest {
    @Test
    fun `y 좌표를 만들 수 있다`() {
        val yCoordinate = YCoordinate(1)
        assertThat(yCoordinate.value).isEqualTo(1)
    }
}
