package domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class XCoordinateTest {

    @Test
    fun `A에서 O 사이의 문자가 아닌 문자로 X 좌표를 생성하려 하면 에러가 발생한다`() {
        assertThatIllegalArgumentException().isThrownBy { XCoordinate.of('Z') }
            .withMessage("x 좌표는 A에서 O 사이의 문자로 생성해야 합니다.")
    }

    @Test
    fun `A에서 O 사이의 문자로 X 좌표를 생성할 수 있다`() {
        assertDoesNotThrow { XCoordinate.of('A') }
    }

    @Test
    fun `소문자로 X 좌표를 생성하려 하면 대문자로 생성한 것과 같은 것을 생성한다`() {
        assertThat(XCoordinate.of('a')).isEqualTo(XCoordinate.of('A'))
    }
}
