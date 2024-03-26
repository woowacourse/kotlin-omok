package omok.model.search

import omok.model.Color
import omok.model.fixture.createPlayingBoard
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class AscendingFiveInRowSearchTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,13,3,3",
        "WHITE,6,1,2",
    )
    fun `우상향대각선 dfs를 할 수 있다`(
        color: Color,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        count: Int,
    ) {
        // given
        val ascendingDfs = AscendingFiveInRowSearch(createPlayingBoard())
        // when
        ascendingDfs.search(color, horizontalCoordinate, verticalCoordinate)
        // then
        Assertions.assertThat(ascendingDfs.count).isEqualTo(count)
    }
}
