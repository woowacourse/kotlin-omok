package woowacourse.omok.model.search

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.model.Color
import woowacourse.omok.model.Color.BLACK
import woowacourse.omok.model.Position
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
                    Position(1, 3),
                    Position(9, 5),
                    Position(2, 3),
                    Position(10, 1),
                    Position(3, 3),
                    Position(10, 3),
                    Position(3, 4),
                    Position(12, 1),
                    Position(3, 5),
                    Position(12, 2),
                    Position(3, 6),
                    Position(11, 2),
                    Position(4, 3),
                    Position(11, 3),
                    Position(4, 4),
                    Position(11, 15),
                    Position(5, 3),
                    Position(11, 14),
                    Position(5, 5),
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
