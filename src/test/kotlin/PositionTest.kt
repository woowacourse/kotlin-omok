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

    @Test
    fun `같은 열인지 위치끼리 판단할 수 있다`() {
        val position = Position(Row.from(1), Col.from('A'))
        val sameColPosition = Position(Row.from(2), Col.from('A'))
        val otherColPosition = Position(Row.from(2), Col.from('B'))

        assertAll(
            { assertTrue(position.isSameCol(sameColPosition)) },
            { assertFalse(position.isSameCol(otherColPosition)) },
        )
    }

    @Test
    fun `증가하는 대각선인지 알 수 있다`() {
        val position = Position(Row.from(1), Col.from('A'))
        val sameIncreasePosition = Position(Row.from(2), Col.from('B'))
        val otherPosition = Position(Row.from(2), Col.from('A'))

        assertAll(
            { assertTrue(position.isIncreasingDiagonal(sameIncreasePosition)) },
            { assertFalse(position.isIncreasingDiagonal(otherPosition)) },
        )
    }
}
