package omok.model.search

import omok.model.Color
import omok.model.fixture.createPlayingBoard
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class HorizontalDfsTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,12,3,4",
        "WHITE,4,2,2",
    )
    fun `수평 dfs를 할 수 있다`(
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
}
