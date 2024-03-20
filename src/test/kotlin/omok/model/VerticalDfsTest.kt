package omok.model

import omok.model.fixture.createPlayingBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class VerticalDfsTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,13,3,5",
        "WHITE,4,3,2",
    )
    fun `수직 dfs를 할 수 있다`(
        color: Color,
        row: Int,
        col: Int,
        count: Int,
    ) {
        // given
        val verticalDfs = VerticalDfs(createPlayingBoard())
        // when
        verticalDfs.search(color, row, col)
        // then
        assertThat(verticalDfs.count).isEqualTo(count)
    }
}
