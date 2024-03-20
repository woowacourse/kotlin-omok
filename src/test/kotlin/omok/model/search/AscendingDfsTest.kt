package omok.model.search

import omok.model.Color
import omok.model.fixture.createPlayingBoard
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class AscendingDfsTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,12,3,3",
        "WHITE,4,2,2",
    )
    fun `우상향대각선 dfs를 할 수 있다`(
        color: Color,
        row: Int,
        col: Int,
        count: Int,
    ) {
        // given
        val ascendingDfs = AscendingDfs(createPlayingBoard())
        // when
        ascendingDfs.search(color, row, col)
        // then
        Assertions.assertThat(ascendingDfs.count).isEqualTo(count)
    }
}
