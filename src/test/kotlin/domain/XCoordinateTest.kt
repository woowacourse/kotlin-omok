package domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class XCoordinateTest {

    @Test
    fun `A에서 O 사이의 문자가 아닌 문자로 X 좌표를 생성하려 하면 에러가 발생한다`() {
        Assertions.assertThatIllegalArgumentException().isThrownBy { XCoordinate.of('Z') }
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
