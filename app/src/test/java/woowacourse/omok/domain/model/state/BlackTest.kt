package woowacourse.omok.domain.model.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.domain.model.fixture.createFourFourBoard
import woowacourse.omok.domain.model.fixture.createOverLineBoard
import woowacourse.omok.domain.model.fixture.createPlayingBoard
import woowacourse.omok.domain.model.fixture.createThreeThreeBoard
import woowacourse.omok.domain.omok.model.Board
import woowacourse.omok.domain.omok.model.GameResult
import woowacourse.omok.domain.omok.model.Position

class BlackTest {
    @Test
    fun `흑돌이 연속으로 다섯 개가 놓였다면 흑이 승리해야 한다`() {
        // given
        val board = createPlayingBoard()
        val blackTurnBoard = Board(_status = board)
        val position = Position.of(3, 'G')
        // when
        blackTurnBoard.place(position)
        val result = blackTurnBoard.getGameResult(position)
        // then
        assertThat(result).isEqualTo(GameResult.WINNER_BLACK)
    }

    @Test
    fun `33 위치라면 흑돌은 착수할 수 없어야 한다`() {
        assertThrows<IllegalArgumentException> {
            val board = createThreeThreeBoard()
            val blackTurnBoard = Board(_status = board)
            val position = Position.of(3, 'C')
            // when
            blackTurnBoard.place(position)
        }
    }

    @Test
    fun `44 위치라면 흑돌은 착수할 수 없어야 한다`() {
        assertThrows<IllegalArgumentException> {
            val board = createFourFourBoard()
            val blackTurnBoard = Board(_status = board)
            val position = Position.of(7, 'G')
            // when
            blackTurnBoard.place(position)
        }
    }

    @Test
    fun `장목 위치라면 흑돌은 착수할 수 없어야 한다`() {
        assertThrows<IllegalArgumentException> {
            val board = createOverLineBoard()
            val blackTurnBoard = Board(_status = board)
            val position = Position.of(3, 'F')
            // when
            blackTurnBoard.place(position)
        }
    }
}
