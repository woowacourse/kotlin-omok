import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class PositionTest {
    @Test
    fun `같은 행인지 위치끼리 판단할 수 있다`() {
        val position = Position(Row.from(1), Col.from('A'))
        val sameRowPosition = Position(Row.from(1), Col.from('B'))
        val otherRowPosition = Position(Row.from(2), Col.from('B'))

        assertAll(
            { assertTrue(position.isSameRow(sameRowPosition)) },
            { assertFalse(position.isSameRow(otherRowPosition)) },
        )
    }
}
