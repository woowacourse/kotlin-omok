package woowacourse.omok.domain.model.search

import woowacourse.omok.domain.omok.model.Color
import woowacourse.omok.domain.model.fixture.createPlayingBoard
import woowacourse.omok.domain.omok.model.search.AscendingDfs
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class AscendingDfsTest {
    @ParameterizedTest
    @CsvSource(
        "BLACK,13,3,3",
        "WHITE,6,1,2",
    )
    fun `우상향대각선 dfs로 우상향 대각선으로 연속된 돌의 개수를 셀 수 있어야 한다`(
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
