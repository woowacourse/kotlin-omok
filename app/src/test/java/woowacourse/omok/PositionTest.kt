import woowacourse.omok.model.Col
import woowacourse.omok.model.Position
import woowacourse.omok.model.Row
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class PositionTest {
    @Test
    fun `같은 행인지 위치끼리 판단할 수 있다`() {
        // given
        val position = PositionFixture.POSITION_1A
        // when
        val sameRowPosition = PositionFixture.POSITION_1B
        val otherRowPosition = PositionFixture.POSITION_2B
        // result
        assertAll(
            { assertTrue(position.row == sameRowPosition.row) },
            { assertFalse(position.row == otherRowPosition.row) },
        )
    }

    @Test
    fun `같은 열인지 위치끼리 판단할 수 있다`() {
        // given
        val position = PositionFixture.POSITION_1A
        // when
        val sameColPosition = PositionFixture.POSITION_2A
        val otherColPosition = PositionFixture.POSITION_2B
        // result
        assertAll(
            { assertTrue(position.col == sameColPosition.col) },
            { assertFalse(position.col == otherColPosition.col) },
        )
    }

    @Test
    fun `증가하는 대각선인지 알 수 있다`() {
        // given
        val position = PositionFixture.POSITION_2B
        // when
        val sameIncreasePositionRight = PositionFixture.POSITION_3C
        val sameIncreasePositionLeft = PositionFixture.POSITION_1A
        val otherPosition = PositionFixture.POSITION_2A
        // result
        assertAll(
            { assertTrue(position.isIncreasingDiagonal(sameIncreasePositionRight)) },
            { assertTrue(position.isIncreasingDiagonal(sameIncreasePositionLeft)) },
            { assertFalse(position.isIncreasingDiagonal(otherPosition)) },
        )
    }

    @Test
    fun `감소하는 대각선인지 알 수 있다`() {
        // given
        val position = PositionFixture.POSITION_3C
        // when
        val decreasePositionRight = PositionFixture.POSITION_2D
        val decreasePositionLeft = PositionFixture.POSITION_4B
        val otherPosition = PositionFixture.POSITION_2A
        // result
        assertAll(
            { assertTrue(position.isDecreasingDiagonal(decreasePositionRight)) },
            { assertTrue(position.isDecreasingDiagonal(decreasePositionLeft)) },
            { assertFalse(position.isDecreasingDiagonal(otherPosition)) },
        )
    }
}

object PositionFixture {
    val POSITION_1A = Position(Row.from(1), Col.from('A'))
    val POSITION_1B = Position(Row.from(1), Col.from('B'))
    val POSITION_1C = Position(Row.from(1), Col.from('C'))

    val POSITION_2A = Position(Row.from(2), Col.from('A'))
    val POSITION_2B = Position(Row.from(2), Col.from('B'))
    val POSITION_2C = Position(Row.from(2), Col.from('C'))
    val POSITION_2D = Position(Row.from(2), Col.from('D'))

    val POSITION_3A = Position(Row.from(3), Col.from('A'))
    val POSITION_3B = Position(Row.from(3), Col.from('B'))
    val POSITION_3C = Position(Row.from(3), Col.from('C'))

    val POSITION_4A = Position(Row.from(4), Col.from('A'))
    val POSITION_4B = Position(Row.from(4), Col.from('B'))
    val POSITION_4C = Position(Row.from(4), Col.from('C'))
}
