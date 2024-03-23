package omok.model.state

import omok.model.Board
import omok.model.GameResult
import omok.model.Position
import omok.model.Stone
import omok.model.fixture.createWhiteWinningBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WhiteTest {
    @Test
    fun `백돌이 연속으로 다섯 개가 놓였다면 백이 승리한다`() {
        // given
        val board = createWhiteWinningBoard()
        val firstStone = Stone.Black(Position.of(1, 'A'))
        val whiteTurnBoard = Board(listOf(firstStone), board)
        val position = Position.of(8, 'C')
        // when
        val result =
            whiteTurnBoard.place(position)
        assertThat(result).isEqualTo(GameResult.WINNER_WHITE)
    }
}
