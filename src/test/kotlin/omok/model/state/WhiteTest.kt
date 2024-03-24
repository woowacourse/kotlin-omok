package omok.model.state

import omok.model.GameResult
import omok.model.Position
import omok.model.fixture.createWhiteWinningBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WhiteTest {
    @Test
    fun `백돌이 연속으로 다섯 개가 놓였다면 백이 승리한다`() {
        // given
        val board = createWhiteWinningBoard()
        val whiteBoard = White(board)
        val position = Position.of(8, 'C')
        // when
        val result =
            whiteBoard.getWinningResult(
                position = position,
                markSinglePlace = { row, col, color ->
                    board[row][col] = color
                },
                addSingleStone = { color, position ->
                },
            )
        assertThat(result).isEqualTo(GameResult.WINNER_WHITE)
    }
}