package woowacourse.omok.model.search

import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.model.Color
import woowacourse.omok.model.Position
import woowacourse.omok.model.fixture.createPlayingBoard

class DescendingFiveInRowSearchTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,11,3,3",
        "WHITE,4,1,3",
    )
    fun `우하향대각선 dfs를 할 수 있다`(
        color: Color,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        count: Int,
    ) {
        // given
        val descendingDfs =
            DescendingFiveInRowSearch(
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
        descendingDfs.search(color, horizontalCoordinate, verticalCoordinate)
        // then
        Assertions.assertThat(descendingDfs.count).isEqualTo(count)
    }
}
