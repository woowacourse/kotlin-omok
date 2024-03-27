package omok.model.state

import omok.model.Color
import omok.model.Color.BLACK
import omok.model.Color.WHITE
import omok.model.GameResult
import omok.model.HorizontalCoordinate
import omok.model.Position
import omok.model.VerticalCoordinate
import omok.model.fixture.createPlayingBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BlackTest {
    @Test
    fun `흑돌이 연속으로 다섯 개가 놓였다면 흑이 승리한다`() {
        // given
        val board =
            createPlayingBoard(
                Position(HorizontalCoordinate.ONE, VerticalCoordinate.C),
                Position(HorizontalCoordinate.NINE, VerticalCoordinate.E),
                Position(HorizontalCoordinate.TWO, VerticalCoordinate.C),
                Position(HorizontalCoordinate.TEN, VerticalCoordinate.A),
                Position(HorizontalCoordinate.THREE, VerticalCoordinate.C),
                Position(HorizontalCoordinate.TEN, VerticalCoordinate.C),
                Position(HorizontalCoordinate.THREE, VerticalCoordinate.D),
                Position(HorizontalCoordinate.TWELVE, VerticalCoordinate.A),
                Position(HorizontalCoordinate.THREE, VerticalCoordinate.E),
                Position(HorizontalCoordinate.TWELVE, VerticalCoordinate.B),
                Position(HorizontalCoordinate.THREE, VerticalCoordinate.F),
                Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.B),
                Position(HorizontalCoordinate.FOUR, VerticalCoordinate.C),
                Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.C),
                Position(HorizontalCoordinate.FOUR, VerticalCoordinate.D),
                Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.O),
                Position(HorizontalCoordinate.FIVE, VerticalCoordinate.C),
                Position(HorizontalCoordinate.ELEVEN, VerticalCoordinate.N),
                Position(HorizontalCoordinate.FIVE, VerticalCoordinate.E),
            )
        val blackBoard = Black(board)
        val position = Position(3, 7)
        // when
        val result =
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { horizontalCoordinate, verticalCoordinate, color ->
                    board[horizontalCoordinate][verticalCoordinate] = color
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
                    board[horizontalCoordinate][verticalCoordinate] = color
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
                    board[horizontalCoordinate][verticalCoordinate] = color
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
                    board[horizontalCoordinate][verticalCoordinate] = color
                },
            )
        }
    }

    private fun createThreeThreeBoard() =
        arrayOf(
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, WHITE, WHITE, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, WHITE, WHITE, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, WHITE, null, WHITE, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, WHITE, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, BLACK, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, BLACK, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, BLACK, BLACK, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
        )

    private fun createFourFourBoard() =
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
            arrayOf<Color?>(null, null, null, null, null, BLACK, null, BLACK, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, BLACK, null, null, BLACK, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, BLACK, null, null, null, BLACK, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
        )

    private fun createOverLineBoard() =
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
            arrayOf<Color?>(null, null, null, null, null, BLACK, null, BLACK, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, BLACK, null, null, BLACK, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, BLACK, BLACK, BLACK, null, BLACK, BLACK, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
            arrayOf<Color?>(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
        )

    companion object {
        private const val EXCEPTION_FORBIDDEN_PLACEMENT = "금수인 위치입니다."
    }
}
