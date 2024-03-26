package omok.model.rule

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ContinualStonesStandardTest {
    @ParameterizedTest
    @ValueSource(ints = [4, 5, 6, 7])
    fun `사목부터 칠목으로 게임을 지정할 수 있다`(count: Int) {
        assertDoesNotThrow {
            ContinualStonesStandard(count)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [3, 8])
    fun `사목부터 칠목이 아니면 지정할 수 없다`(count: Int) {
        assertThrows<IllegalArgumentException> {
            ContinualStonesStandard(count)
        }
    }
}
