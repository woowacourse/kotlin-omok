package domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class YCoordinateTest {

    @Test
    fun `1에서 15 사이가 아닌 숫자로 Y 좌표를 생성하려 하면 에러가 발생한다`() {
        assertThatIllegalArgumentException().isThrownBy { YCoordinate.of(0) }
            .withMessage("y 좌표는 1에서 15 사이의 숫자로 생성해야 합니다.")
    }

    @Test
    fun `1에서 15 사이의 숫자로 Y 좌표를 생성할 수 있다`() {
        assertDoesNotThrow { YCoordinate.of(15) }
    }
}
