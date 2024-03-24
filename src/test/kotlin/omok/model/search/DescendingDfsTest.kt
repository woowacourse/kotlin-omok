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
    fun `우하향대각선 dfs를 할 수 있다`(
        color: Color,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        count: Int,
    ) {
        // given
        val descendingDfs = DescendingDfs(createPlayingBoard())
        // when
        descendingDfs.search(color, horizontalCoordinate, verticalCoordinate)
        // then
        Assertions.assertThat(descendingDfs.count).isEqualTo(count)
    }
}
