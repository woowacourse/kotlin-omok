package model.domain.tools

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CoordinationTest {
    @ValueSource(ints = [0, 8, 14])
    @ParameterizedTest
    fun `좌표는 0 이상 14 이하이다`(value: Int) {
        assertDoesNotThrow {
            Coordination.from(value)
        }
    }

    @ValueSource(ints = [-100, -1, 15, 100])
    @ParameterizedTest
    fun `좌표는 0 미만 14 초과일 수 없다`(value: Int) {
        assertThrows<IllegalArgumentException> { Coordination.from(value) }
    }
}
