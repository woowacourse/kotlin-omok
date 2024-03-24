package omok.model.search

import omok.model.Color
import omok.model.fixture.createPlayingBoard
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class DescendingDfsTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,11,3,3",
        "WHITE,4,1,3",
    )
    fun `우하향대각선 dfs로 우하향 대각선으로 연속된 돌의 개수를 셀 수 있어야 한다`(
        color: Color,
        row: Int,
        col: Int,
        count: Int,
    ) {
        // given
        val descendingDfs = DescendingDfs(createPlayingBoard())
        // when
        descendingDfs.search(color, row, col)
        // then
        Assertions.assertThat(descendingDfs.count).isEqualTo(count)
    }
}
