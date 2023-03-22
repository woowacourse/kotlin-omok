package omok.model.external

import omok.model.external.rule.BlackRenjuRule
import omok.model.external.rule.OmokRule
import omok.model.external.rule.type.Foul
import omok.model.external.rule.type.Violation
import omok.model.external.rule.wrapper.point.Point
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
    fun `흑돌이 33이면 반칙이다`() {
        // given
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

        // when
        val expected = renjuRule.checkDoubleFoul(blackStones, whiteStones, newStone, Foul.DOUBLE_THREE)

        // then
        assertThat(expected).isEqualTo(Violation.DOUBLE_THREE)
    }

    @ParameterizedTest
    @CsvSource("8, 3", "12, 6", "10, 10", "8, 9", "5, 8")
    fun `흑돌이 44이면 반칙이다`(newStoneRow: Int, newStoneCol: Int) {
        // given
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

        // when
        val expected = renjuRule.checkDoubleFoul(blackStones, whiteStones, newStone, Foul.DOUBLE_FOUR)

        assertThat(expected).isEqualTo(Violation.DOUBLE_FOUR)
    }

    @Test
    fun `흑돌이 장목이면 반칙이다`() {
        // given
        val blackStones = listOf(
            Point(5, 5),
            Point(6, 6),
            Point(7, 7),
            Point(9, 9),
            Point(10, 10),
        )
        val newStone = Point(8, 8)

        // when
        val expected = renjuRule.checkOverline(blackStones, newStone)

        // then
        assertThat(expected).isEqualTo(Violation.OVERLINE)
    }

    @Test
    fun `흑돌이 33수를 놓으면서 동시에 오목도 만들어지는 경우에는 흑돌 승리로 인정된다`() {
        // given
        val blackStones = listOf(
            Point(5, 5), Point(5, 7),
            Point(5, 8), Point(5, 9),
            Point(4, 5), Point(4, 6),
            Point(6, 6), Point(6, 7),
        )
        val whiteStones = listOf<Point>()
        val newStone = Point(5, 6)

        // when
        val expected = renjuRule.checkWin(blackStones, whiteStones, newStone)

        // then
        assertThat(expected).isTrue
    }

    @Test
    fun `흑돌이 44수를 놓으면서 동시에 오목도 만들어지는 경우에는 흑돌 승리로 인정된다`() {
        // given
        val blackStones = listOf(
            Point(5, 5), Point(5, 6),
            Point(5, 8), Point(5, 9),
            Point(6, 6), Point(6, 8),
            Point(4, 6), Point(4, 8),
            Point(3, 5), Point(3, 9),
        )
        val whiteStones = listOf<Point>()
        val newStone = Point(5, 7)

        // when
        val expected = renjuRule.checkWin(blackStones, whiteStones, newStone)

        // then
        assertThat(expected).isTrue
    }
}
