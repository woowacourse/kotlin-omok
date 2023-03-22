package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class StonePositionTest {

    @Test
    fun `x좌표와 y좌표를 알고 있다`() {
        val stonePosition: StonePosition = StonePosition.from(x = 5, y = 7)
        assertAll(
            { assertThat(stonePosition.x).isEqualTo(5) },
            { assertThat(stonePosition.y).isEqualTo(7) },
        )
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 15])
    fun `x좌표는 1이상 15이하의 값을 가진다`(x: Int) {
        assertDoesNotThrow {
            StonePosition.from(x, y = 5)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 16])
    fun `x좌표가 1미만 15초과인 경우 에러가 발생한다`(x: Int) {
        assertThrows<IllegalArgumentException> {
            StonePosition.from(x, y = 5)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 15])
    fun `y좌표는 1이상 15이하의 값을 가진다`(y: Int) {
        assertDoesNotThrow {
            StonePosition.from(x = 5, y)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 16])
    fun `y좌표가 1미만 15초과인 경우 에러가 발생한다`(y: Int) {
        assertThrows<IllegalArgumentException> {
            StonePosition.from(x = 5, y)
        }
    }
}
