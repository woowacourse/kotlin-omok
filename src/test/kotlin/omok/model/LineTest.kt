package omok.model

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LineTest {
    @ParameterizedTest
    @ValueSource(ints = [-1, 15])
    fun `줄은 0부터 14까지의 값을 가진다`(input: Int) {
        assertThrows<IllegalArgumentException> { Line(input) }
    }
}
