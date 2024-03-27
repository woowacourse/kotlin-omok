package omok.model.search

import omok.model.Color
import omok.model.Color.BLACK
import omok.model.HorizontalCoordinate
import omok.model.Position
import omok.model.VerticalCoordinate
import omok.model.fixture.createPlayingBoard
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class HorizontalFiveInRowSearchTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,13,3,4",
        "WHITE,4,2,2",
    )
    fun `수평 dfs를 할 수 있다`(
        color: Color,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        count: Int,
    ) {
        // given
        val horizontalDfs =
            HorizontalFiveInRowSearch(
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
        horizontalDfs.search(color, horizontalCoordinate, verticalCoordinate)
        // then
        Assertions.assertThat(horizontalDfs.count).isEqualTo(count)
    }

    @Test
    fun `가장자리 수평 dfs 테스트`() {
        val horizontalDfs =
            HorizontalFiveInRowSearch(
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
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
                    arrayOf(null, BLACK, BLACK, BLACK, BLACK, BLACK, null, null, null, null, null, null, null, null, null, null),
                ),
            )
        horizontalDfs.search(BLACK, 15, 2)
        Assertions.assertThat(horizontalDfs.count).isEqualTo(5)
    }
}
