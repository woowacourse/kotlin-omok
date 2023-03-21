package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class YCoordinateTest {
    @Test
    fun `y 좌표를 만들 수 있다`() {
        val yCoordinate = YCoordinate(1)
        assertThat(yCoordinate.value).isEqualTo(1)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 15])
    fun `y 좌표는 1부터 15사이 다`(value: Int) {
        assertDoesNotThrow { YCoordinate(value) }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 16])
    fun `y 좌표는 1부터 15사이 이외는 에러가 발생한다`(value: Int) {
        val exception = assertThrows<IllegalArgumentException> { YCoordinate(value) }
        assertThat(exception.message).isEqualTo("Y 좌표의 범위는 1부터 15까지 입니다.")
    }
}
