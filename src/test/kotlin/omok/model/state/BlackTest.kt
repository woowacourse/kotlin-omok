package omok.model.state

import omok.model.GameResult
import omok.model.Position
import omok.model.fixture.createFourFourBoard
import omok.model.fixture.createOverLineBoard
import omok.model.fixture.createPlayingBoard
import omok.model.fixture.createThreeThreeBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BlackTest {
    @Test
    fun `흑돌이 연속으로 다섯 개가 놓였다면 흑이 승리한다`() {
        // given
        val board = createPlayingBoard()
        val blackBoard = Black(board)
        val position = Position.of(3, 7)
        // when
        val result =
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { horizontalCoordinate, verticalCoordinate, color ->
                    board[horizontalCoordinate][verticalCoordinate] = color
                },
                addSingleStone = { color, position -> Unit },
            )

        assertThat(result).isEqualTo(GameResult.WINNER_BLACK)
    }

    @Test
    fun `33 조건일 때, 흑돌은 착수할 수 없다`() {
        val board = createThreeThreeBoard()
        val blackBoard = Black(board)
        val position = Position.of(3, 3)
        // when
        assertThrows<IllegalArgumentException>(EXCEPTION_FORBIDDEN_PLACEMENT) {
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { horizontalCoordinate, verticalCoordinate, color ->
                    board[horizontalCoordinate][verticalCoordinate] = color
                },
                addSingleStone = { color, position -> Unit },
            )
        }
    }

    @Test
    fun `44 조건일 때, 흑돌은 착수할 수 없다`() {
        val board = createFourFourBoard()
        val blackBoard = Black(board)
        val position = Position.of(7, 7)
        // when
        assertThrows<IllegalArgumentException>(EXCEPTION_FORBIDDEN_PLACEMENT) {
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { horizontalCoordinate, verticalCoordinate, color ->
                    board[horizontalCoordinate][verticalCoordinate] = color
                },
                addSingleStone = { color, position -> Unit },
            )
        }
    }

    @Test
    fun `장목 조건일 때, 흑돌은 착수할 수 없다`() {
        val board = createOverLineBoard()
        val blackBoard = Black(board)
        val position = Position.of(3, 6)
        // when
        assertThrows<IllegalArgumentException>(EXCEPTION_FORBIDDEN_PLACEMENT) {
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { horizontalCoordinate, verticalCoordinate, color ->
                    board[horizontalCoordinate][verticalCoordinate] = color
                },
                addSingleStone = { color, position -> Unit },
            )
        }
    }

    companion object {
        private const val EXCEPTION_FORBIDDEN_PLACEMENT = "금수인 위치입니다."
    }
}
