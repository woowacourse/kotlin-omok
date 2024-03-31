package woowacourse.omok.domain.model.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.fixture.createWhiteWinningBoard
import woowacourse.omok.domain.omok.model.Board
import woowacourse.omok.domain.omok.model.GameResult
import woowacourse.omok.domain.omok.model.Place
import woowacourse.omok.domain.omok.model.Position

class WhiteTest {
    @Test
    fun `백돌이 연속으로 다섯 개가 놓였다면 백이 승리해야 한다`() {
        // given
        val board = createWhiteWinningBoard()
        val firstPlace = Place("흑", 1, 1)
        val whiteTurnBoard = Board(listOf(firstPlace), board)
        val position = Position.of(8, 'C')
        // when
        whiteTurnBoard.place(position)
        val result = whiteTurnBoard.getGameResult(position)
        assertThat(result).isEqualTo(GameResult.WINNER_WHITE)
    }
}
