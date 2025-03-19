package omok.model

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LineTest {
    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 16])
    fun `줄은 1부터 15까지의 값을 가진다`(input: Int) {
        assertThrows<IllegalArgumentException> { Line(input) }
    }
}
