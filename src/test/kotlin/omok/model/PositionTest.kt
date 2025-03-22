package omok.model

import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PositionTest {
    @ParameterizedTest
    @ValueSource(ints = [-1, 15, 17])
    fun `Row의 범위를 벗어날 수 없다`(row: Int) {
        assertThrows<IllegalArgumentException> { Position(Row(row), Col(1)) }
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 15, 18])
    fun `Col의 범위를 벗어날 수 없다`(col: Int) {
        assertThrows<IllegalArgumentException> { Position(Row(1), Col(col)) }
    }
}
