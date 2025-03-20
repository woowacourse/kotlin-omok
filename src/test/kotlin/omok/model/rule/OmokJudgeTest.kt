package omok.model.rule

import omok.fixture.doubleFourForbiddenBoard
import omok.fixture.doubleThreeForbiddenBoard
import omok.fixture.falseDoubleThreeBoard
import omok.fixture.fourThreeBoard
import omok.fixture.overlineForbiddenBoard
import omok.model.board.Point
import omok.model.stone.Position
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class OmokJudgeTest {
    private lateinit var judge: OmokJudge

    @BeforeEach
    fun setUp() {
        judge = OmokJudge()
    }

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
        val actual = judge.validate(doubleThreeForbiddenBoard, Point(Position(x, y)))
        val expected = false

        assertEquals(expected, actual)
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
        val actual = judge.validate(doubleFourForbiddenBoard, Point(Position(x, y)))
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `장목인 경우 흑돌을 둘 수 없다`() {
        val actual = judge.validate(overlineForbiddenBoard, Point(Position(5, 1)))
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun `4-3인 경우 흑돌을 둘 수 있다`() {
        val actual = judge.validate(fourThreeBoard, Point(Position(8, 5)))
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun `3-3 거짓 금수인 경우 흑돌을 둘 수 있다`() {
        val actual = judge.validate(falseDoubleThreeBoard, Point(Position(4, 3)))
        val expected = true

        assertEquals(expected, actual)
    }
}
