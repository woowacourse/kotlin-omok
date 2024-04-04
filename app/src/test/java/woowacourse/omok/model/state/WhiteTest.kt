package woowacourse.omok.model.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.Color.BLACK
import woowacourse.omok.model.Color.WHITE
import woowacourse.omok.model.GameResult
import woowacourse.omok.model.Position
import woowacourse.omok.model.Row
import woowacourse.omok.model.Rows

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
                    board.values[horizontalCoordinate].placementData[verticalCoordinate] = color
                },
            )
        assertThat(result).isEqualTo(GameResult.WINNER_WHITE)
    }

    private fun createWhiteWinningBoard() =
        Rows(
            listOf(
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, WHITE, WHITE, WHITE, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, WHITE, WHITE, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, WHITE, null, WHITE, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, WHITE, null, WHITE, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, BLACK, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, BLACK, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, BLACK, BLACK, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
            ),
        )
}
