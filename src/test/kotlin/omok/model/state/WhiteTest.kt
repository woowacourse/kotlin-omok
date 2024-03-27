package omok.model.state

import omok.model.Color
import omok.model.Color.BLACK
import omok.model.Color.WHITE
import omok.model.GameResult
import omok.model.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WhiteTest {
    @Test
    fun `백돌이 연속으로 다섯 개가 놓였다면 백이 승리한다`() {
        // given
        val board = createWhiteWinningBoard()
        val whiteBoard = White(board)
        val position = Position(8, 3)
        // when
        val result =
            whiteBoard.getWinningResult(
                position = position,
                markSinglePlace = { horizontalCoordinate, verticalCoordinate, color ->
                    board[horizontalCoordinate][verticalCoordinate] = color
                },
            )
        assertThat(result).isEqualTo(GameResult.WINNER_WHITE)
    }

    private fun createWhiteWinningBoard() =
        arrayOf(
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, WHITE, WHITE, WHITE, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, WHITE, WHITE, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, WHITE, null, WHITE, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, WHITE, null, WHITE, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, BLACK, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, BLACK, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, BLACK, BLACK, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
        )
}
