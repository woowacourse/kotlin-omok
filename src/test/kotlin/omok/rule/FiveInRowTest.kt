package omok.rule

import omok.board.Point
import omok.board.PointState
import omok.fixture.antiDiagonalFiveInRowBoard
import omok.fixture.diagonalFiveInRowBoard
import omok.fixture.horizontalFiveInRowBoard
import omok.fixture.verticalFiveInRowBoard
import omok.stone.Position
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FiveInRowTest {
    private lateinit var fiveInRow: FiveInRow
    private lateinit var point: Point

    @BeforeEach
    fun setUp() {
        fiveInRow = FiveInRow()
    }

    @Test
    fun `세로로 오목인 경우 true를 반환한다`() {
        point = Point(Position(5, 1)).apply { changeState(PointState.WHITE) }
        assertTrue(fiveInRow.calculate(verticalFiveInRowBoard, point))
    }

    @Test
    fun `가로로 오목인 경우 true를 반환한다`() {
        point = Point(Position(1, 5)).apply { changeState(PointState.WHITE) }
        assertTrue(fiveInRow.calculate(horizontalFiveInRowBoard, point))
    }

    @Test
    fun `대각선으로 오목인 경우 true를 반환한다`() {
        point = Point(Position(5, 5)).apply { changeState(PointState.BLACK) }
        assertTrue(fiveInRow.calculate(diagonalFiveInRowBoard, point))
    }

    @Test
    fun `반대 대각선으로 오목인 경우 true를 반환한다`() {
        point = Point(Position(5, 1)).apply { changeState(PointState.BLACK) }
        assertTrue(fiveInRow.calculate(antiDiagonalFiveInRowBoard, point))
    }

    @Test
    fun `오목이 없는 경우 false를 반환한다`() {
        point = Point(Position(14, 1)).apply { changeState(PointState.WHITE) }
        assertFalse(fiveInRow.calculate(horizontalFiveInRowBoard, point))
    }
}
