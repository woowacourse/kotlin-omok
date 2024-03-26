package omok.model.search

import omok.model.Color
import omok.model.Color.BLACK
import omok.model.Color.NONE
import omok.model.fixture.createPlayingBoard
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class HorizontalDfsTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,13,3,4",
        "WHITE,4,2,2",
    )
    fun `수평 dfs로 수평으로 연속된 돌의 개수를 셀 수 있어야 한다`(
        color: Color,
        row: Int,
        col: Int,
        count: Int,
    ) {
        // given
        val horizontalDfs = HorizontalDfs(createPlayingBoard())
        // when
        horizontalDfs.search(color, row, col)
        // then
        Assertions.assertThat(horizontalDfs.count).isEqualTo(count)
    }

    @Test
    fun `흑돌이 수평으로 5개가 연속해서 놓여있다면, dfs 탐색을 통해 5개의 흑돌을 셀 수 있어야 한다`() {
        val horizontalDfs =
            HorizontalDfs(
                arrayOf(
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                    arrayOf(NONE, BLACK, BLACK, BLACK, BLACK, BLACK, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE),
                ),
            )
        horizontalDfs.search(BLACK, 15, 2)
        Assertions.assertThat(horizontalDfs.count).isEqualTo(5)
    }
}
