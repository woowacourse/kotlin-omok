package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class XCoordinateTest {
    @Test
    fun `x 좌표를 만들 수 있다`() {
        val xCoordinate = XCoordinate(1)
        assertThat(xCoordinate.value).isEqualTo(1)
    }

    @ParameterizedTest
    @ValueSource(chars = ['A', 'O'])
    fun `x 좌표는 A부터 O사이 다`(value: Char) {
        assertDoesNotThrow { XCoordinate(value) }
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 15])
    fun `x 좌표는 숫자로도 만들 수 있다`(value: Int) {
        assertDoesNotThrow { XCoordinate(value) }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 16])
    fun `x 좌표는 1부터 15사이 이외는 에러가 발생한다`(value: Int) {
        assertThrows<IllegalArgumentException> { XCoordinate(value) }
    }

    @Test
    fun `x 좌표는 A부터 O사이 이외는 에러가 발생한다`() {
        assertThrows<IllegalArgumentException> { XCoordinate('P') }
    }
}
