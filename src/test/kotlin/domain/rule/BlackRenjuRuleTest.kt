package domain.rule

import domain.point.Point
import domain.rule.type.Foul
import domain.rule.type.Violation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BlackRenjuRuleTest {
    private lateinit var renjuRule: OmokRule

    @BeforeEach
    fun setUp() {
        renjuRule = BlackRenjuRule(15, 15)
    }

    @ParameterizedTest
    @CsvSource("3, 5", "12, 4", "3, 5", "4, 11", "11, 12")
    fun `흑의 3-3을 판단할 수 있다`() {
        val blackStones = listOf(
            Point(3, 3), Point(3, 4), Point(4, 4),
            Point(5, 3), Point(12, 3), Point(12, 5),
            Point(13, 4), Point(14, 4), Point(6, 2),
            Point(5, 5), Point(6, 5), Point(3, 11),
            Point(6, 11), Point(4, 13), Point(4, 14),
            Point(9, 14), Point(10, 13), Point(12, 13),
            Point(9, 10),
        )
        val whiteStones = listOf(Point(9, 9))
        val newStone = Point(3, 5)

        val expected = renjuRule.checkDoubleFoul(blackStones, whiteStones, newStone, Foul.DOUBLE_THREE)

        assertThat(expected).isEqualTo(Violation.DOUBLE_THREE)
    }

    @ParameterizedTest
    @CsvSource("8, 3", "12, 6", "10, 10", "8, 9", "5, 8")
    fun `흑의 4-4를 판단할 수 있다`(newStoneRow: Int, newStoneCol: Int) {
        val blackStones = listOf(
            Point(15, 3), Point(14, 3), Point(12, 3),
            Point(11, 3), Point(10, 3), Point(12, 4),
            Point(12, 7), Point(12, 9), Point(12, 10),
            Point(9, 10), Point(8, 10), Point(6, 10),
            Point(8, 11), Point(8, 8), Point(7, 8),
            Point(6, 8), Point(6, 5), Point(5, 5),
            Point(5, 6), Point(5, 7), Point(4, 7),
        )
        val whiteStones = listOf(
            Point(5, 4),
            Point(9, 8),
        )
        val newStone = Point(newStoneRow, newStoneCol)

        val expected = renjuRule.checkDoubleFoul(blackStones, whiteStones, newStone, Foul.DOUBLE_FOUR)

        assertThat(expected).isEqualTo(Violation.DOUBLE_FOUR)
    }

    @Test
    fun `흑의 장목을 판단할 수 있다`() {
        val blackStones = listOf(
            Point(5, 5),
            Point(6, 6),
            Point(7, 7),
            Point(9, 9),
            Point(10, 10),
        )
        val newStone = Point(8, 8)

        val expected = renjuRule.checkOverline(blackStones, newStone)

        assertThat(expected).isEqualTo(Violation.OVERLINE)
    }

    @Test
    fun `연속 흑돌이 5개이면 승리이다`() {
        val blackStones = listOf(
            Point(5, 5), Point(5, 6),
            Point(5, 8), Point(5, 9),
        )
        val whiteStones = listOf<Point>()
        val newStone = Point(5, 7)

        val expected = renjuRule.checkWin(blackStones, whiteStones, newStone)

        assertThat(expected).isTrue
    }
    @Test
    fun `4-4이더라도 연속 흑돌이 5개이면 승리이다`() {
        val blackStones = listOf(
            Point(5, 5), Point(5, 6),
            Point(5, 8), Point(5, 9),
            Point(6, 6), Point(6, 8),
            Point(4, 6), Point(4, 8),
            Point(3, 5), Point(3, 9),
        )
        val whiteStones = listOf<Point>()
        val newStone = Point(5, 7)

        val expected = renjuRule.checkWin(blackStones, whiteStones, newStone)

        assertThat(expected).isTrue
    }

    @Test
    fun `3-3이더라도 연속 흑돌이 5개면 승리이다`() {
        val blackStones = listOf(
            Point(5, 5), Point(5, 7),
            Point(5, 8), Point(5, 9),
            Point(4, 5), Point(4, 6),
            Point(6, 6), Point(6, 7),
        )
        val whiteStones = listOf<Point>()
        val newStone = Point(5, 6)

        val expected = renjuRule.checkWin(blackStones, whiteStones, newStone)

        assertThat(expected).isTrue
    }
}
