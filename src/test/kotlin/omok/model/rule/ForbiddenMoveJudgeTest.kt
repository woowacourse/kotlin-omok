package omok.model.rule

import omok.fixture.generateTestBoardFixture
import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ForbiddenMoveJudgeTest {
    @ParameterizedTest
    @CsvSource(
        "4,12",
        "5,3",
        "12,11",
        "11,4",
    )
    fun `3-3인 경우 흑돌을 둘 수 없다`(
        x: Int,
        y: Int,
    ) {
        val doubleThreeForbiddenBoard: Board =
            generateTestBoardFixture(
                listOf(
                    Point(3, 12), Point(5, 12), Point(4, 14), Point(4, 13),
                    Point(2, 6), Point(3, 5), Point(5, 6), Point(5, 5),
                    Point(10, 9), Point(13, 12), Point(13, 10), Point(14, 9),
                    Point(11, 6), Point(11, 3), Point(13, 4), Point(14, 4),
                ),
                PointState.BLACK,
            )
        val actual = ForbiddenMoveJudge.validate(doubleThreeForbiddenBoard, Point(x, y))
        assertFalse(actual)
    }

    @ParameterizedTest
    @CsvSource(
        "7,7",
        "6,12",
    )
    fun `4-4인 경우 흑돌을 둘 수 없다`(
        x: Int,
        y: Int,
    ) {
        val doubleFourForbiddenBoard: Board =
            generateTestBoardFixture(
                listOf(
                    Point(5, 5), Point(6, 6), Point(8, 8), Point(8, 6), Point(9, 5),
                    Point(6, 8), Point(8, 12), Point(7, 7), Point(5, 12), Point(7, 12),
                    Point(6, 11), Point(6, 13), Point(6, 14),
                ),
                PointState.BLACK,
            )
        val actual = ForbiddenMoveJudge.validate(doubleFourForbiddenBoard, Point(x, y))
        assertFalse(actual)
    }

    @Test
    fun `장목인 경우 흑돌을 둘 수 없다`() {
        val overlineForbiddenBoard: Board =
            generateTestBoardFixture(
                listOf(Point(1, 1), Point(2, 1), Point(3, 1), Point(4, 1), Point(6, 1)),
                PointState.BLACK,
            )
        val actual = ForbiddenMoveJudge.validate(overlineForbiddenBoard, Point(5, 1))
        assertFalse(actual)
    }

    @Test
    fun `4-3인 경우 흑돌을 둘 수 있다`() {
        val fourThreeBoard: Board =
            generateTestBoardFixture(
                listOf(Point(5, 5), Point(6, 5), Point(7, 5), Point(8, 6), Point(8, 7)),
                PointState.BLACK,
            )
        val actual = ForbiddenMoveJudge.validate(fourThreeBoard, Point(8, 5))
        assertTrue(actual)
    }

    @Test
    fun `3-3 거짓 금수인 경우 흑돌을 둘 수 있다`() {
        val falseDoubleThreeBoard: Board =
            generateTestBoardFixture(
                listOf(Point(3, 3), Point(5, 3), Point(4, 2), Point(4, 4)),
                PointState.WHITE,
            )
        val actual = ForbiddenMoveJudge.validate(falseDoubleThreeBoard, Point(4, 3))
        assertTrue(actual)
    }
}
