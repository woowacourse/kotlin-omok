package woowacourse.omok.model.search

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.model.Color
import woowacourse.omok.model.Color.BLACK
import woowacourse.omok.model.Position
import woowacourse.omok.model.Row
import woowacourse.omok.model.Rows
import woowacourse.omok.model.fixture.createPlayingBoard

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
        horizontalDfs.search(color, horizontalCoordinate, verticalCoordinate)
        // then
        Assertions.assertThat(horizontalDfs.count).isEqualTo(count)
    }

    @Test
    fun `가장자리 수평 dfs 테스트`() {
        val horizontalDfs =
            HorizontalFiveInRowSearch(
                Rows(
                    listOf(
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                        Row(
                            mutableListOf(null, BLACK, BLACK, BLACK, BLACK, BLACK, null, null, null, null, null, null, null, null, null, null),
                        ),
                    ),
                ),
            )
        horizontalDfs.search(BLACK, 15, 2)
        Assertions.assertThat(horizontalDfs.count).isEqualTo(5)
    }
}
