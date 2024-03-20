package omok.model

import omok.model.fixture.createPlayingBoard
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class DescendingDfsTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,11,4,3",
        "WHITE,4,2,3",
    )
    fun `우하향대각선 dfs를 할 수 있다`(
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
