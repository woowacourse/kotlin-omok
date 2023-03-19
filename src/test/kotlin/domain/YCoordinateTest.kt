package domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class YCoordinateTest {

    @Test
    fun `1에서 15 사이의 숫자로 Y 좌표를 생성할 수 있다`() {
        assertDoesNotThrow { YCoordinate.of(10) }
    }

    @Test
    fun `같은 숫자의 YCoordinate에 접근하면 같은 객체를 반환한다`() {
        assertThat(YCoordinate.of(15)).isEqualTo(YCoordinate.of(15))
    }
}
