package woowacourse.omok.model.search

import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.model.Color
import woowacourse.omok.model.HorizontalCoordinate
import woowacourse.omok.model.Position
import woowacourse.omok.model.VerticalCoordinate
import woowacourse.omok.model.fixture.createPlayingBoard

class AscendingFiveInRowSearchTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,13,3,3",
        "WHITE,6,1,2",
    )
    fun `우상향대각선 dfs를 할 수 있다`(
        color: Color,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        count: Int,
    ) {
        // given
        val ascendingDfs =
            AscendingFiveInRowSearch(
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
        ascendingDfs.search(color, horizontalCoordinate, verticalCoordinate)
        // then
        Assertions.assertThat(ascendingDfs.count).isEqualTo(count)
    }
}
