package woowacourse.omok.model.search

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.model.Color
import woowacourse.omok.model.Color.BLACK
import woowacourse.omok.model.HorizontalCoordinate
import woowacourse.omok.model.Position
import woowacourse.omok.model.VerticalCoordinate
import woowacourse.omok.model.fixture.createPlayingBoard

class VerticalFiveInRowSearchTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,15,3,5",
        "WHITE,5,2,2",
    )
    fun `수직 dfs를 할 수 있다`(
        color: Color,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        count: Int,
    ) {
        // given
        val verticalDfs =
            VerticalFiveInRowSearch(
                createPlayingBoard(
                    Position(HorizontalCoordinate.ONE, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.NINE, VerticalCoordinate.E),
                    Position(HorizontalCoordinate.TWO, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.TEN, VerticalCoordinate.A),
                    Position(HorizontalCoordinate.THREE, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.TEN, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.THREE, VerticalCoordinate.D),
                    Position(HorizontalCoordinate.TWELVE, VerticalCoordinate.A),
                    Position(HorizontalCoordinate.THREE, VerticalCoordinate.E),
                    Position(HorizontalCoordinate.TWELVE, VerticalCoordinate.B),
                    Position(HorizontalCoordinate.THREE, VerticalCoordinate.F),
                    Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.B),
                    Position(HorizontalCoordinate.FOUR, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.FOUR, VerticalCoordinate.D),
                    Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.O),
                    Position(HorizontalCoordinate.FIVE, VerticalCoordinate.C),
                    Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.N),
                    Position(HorizontalCoordinate.FIVE, VerticalCoordinate.E),
                ),
            )
        // when
        verticalDfs.search(color, horizontalCoordinate, verticalCoordinate)
        // then
        assertThat(verticalDfs.count).isEqualTo(count)
    }

    @Test
    fun `가장자리 수직 dfs 테스트`() {
        val verticalDfs =
            VerticalFiveInRowSearch(
                arrayOf(
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, BLACK, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, BLACK, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, BLACK, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, BLACK, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, BLACK, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                ),
            )
        verticalDfs.search(BLACK, 15, 1)
        assertThat(verticalDfs.count).isEqualTo(5)
    }
}
