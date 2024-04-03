package woowacourse.omok.model.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.model.Color.BLACK
import woowacourse.omok.model.Color.WHITE
import woowacourse.omok.model.GameResult
import woowacourse.omok.model.Position
import woowacourse.omok.model.Row
import woowacourse.omok.model.Rows
import woowacourse.omok.model.fixture.createPlayingBoard

class BlackTest {
    @Test
    fun `흑돌이 연속으로 다섯 개가 놓였다면 흑이 승리한다`() {
        // given
        val board =
            createPlayingBoard(
                Position(1, 3),
                Position(9, 5),
                Position(2, 3),
                Position(10, 1),
                Position(3, 3),
                Position(10, 3),
                Position(3, 4),
                Position(12, 1),
                Position(3, 5),
                Position(12, 2),
                Position(3, 6),
                Position(11, 2),
                Position(4, 3),
                Position(11, 3),
                Position(4, 4),
                Position(11, 15),
                Position(5, 3),
                Position(11, 14),
                Position(5, 5),
            )
        val blackBoard = Black(board)
        val position = Position(3, 7)
        // when
        val result =
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { horizontalCoordinate, verticalCoordinate, color ->
                    board.values[horizontalCoordinate].placementData[verticalCoordinate] = color
                },
            )

        assertThat(result).isEqualTo(GameResult.WINNER_BLACK)
    }

    @Test
    fun `33 조건일 때, 흑돌은 착수할 수 없다`() {
        val board = createThreeThreeBoard()
        val blackBoard = Black(board)
        val position = Position(3, 3)
        // when
        assertThrows<IllegalArgumentException>(EXCEPTION_FORBIDDEN_PLACEMENT) {
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { horizontalCoordinate, verticalCoordinate, color ->
                    board.values[horizontalCoordinate].placementData[verticalCoordinate] = color
                },
            )
        }
    }

    @Test
    fun `44 조건일 때, 흑돌은 착수할 수 없다`() {
        val board = createFourFourBoard()
        val blackBoard = Black(board)
        val position = Position(7, 7)
        // when
        assertThrows<IllegalArgumentException>(EXCEPTION_FORBIDDEN_PLACEMENT) {
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { horizontalCoordinate, verticalCoordinate, color ->
                    board.values[horizontalCoordinate].placementData[verticalCoordinate] = color
                },
            )
        }
    }

    @Test
    fun `장목 조건일 때, 흑돌은 착수할 수 없다`() {
        val board = createOverLineBoard()
        val blackBoard = Black(board)
        val position = Position(3, 6)
        // when
        assertThrows<IllegalArgumentException>(EXCEPTION_FORBIDDEN_PLACEMENT) {
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { horizontalCoordinate, verticalCoordinate, color ->
                    board.values[horizontalCoordinate].placementData[verticalCoordinate] = color
                },
            )
        }
    }

    private fun createThreeThreeBoard() =
        Rows(
            listOf(
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, WHITE, WHITE, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, WHITE, WHITE, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, WHITE, null, WHITE, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, WHITE, null, null, null, null, null, null, null, null, null, null)),
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

    private fun createFourFourBoard() =
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
                Row(mutableListOf(null, null, null, null, null, BLACK, null, BLACK, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, BLACK, null, null, BLACK, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, BLACK, null, null, null, BLACK, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
            ),
        )

    private fun createOverLineBoard() =
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
                Row(mutableListOf(null, null, null, null, null, BLACK, null, BLACK, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, BLACK, null, null, BLACK, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, BLACK, BLACK, BLACK, null, BLACK, BLACK, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
                Row(mutableListOf(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)),
            ),
        )

    companion object {
        private const val EXCEPTION_FORBIDDEN_PLACEMENT = "금수인 위치입니다."
    }
}
