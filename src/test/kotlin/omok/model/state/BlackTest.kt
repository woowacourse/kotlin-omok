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
        val position = Position.of(3, 'G')
        // when
        val result =
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { row, col, color ->
                    board[row][col] = color
                },
                addSingleStone = { color, position -> Unit },
            )

        assertThat(result).isEqualTo(GameResult.WINNER_BLACK)
    }

    @Test
    fun `33 조건일 때, 흑돌은 착수할 수 없다`() {
        assertThrows<IllegalArgumentException> {
            val board = createThreeThreeBoard()
            val blackBoard = Black(board)
            val position = Position.of(3, 'C')
            // when
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { row, col, color ->
                    board[row][col] = color
                },
                addSingleStone = { color, position -> Unit },
            )
        }
    }

    @Test
    fun `44 조건일 때, 흑돌은 착수할 수 없다`() {
        assertThrows<IllegalArgumentException> {
            val board = createFourFourBoard()
            val blackBoard = Black(board)
            val position = Position.of(7, 'G')
            // when
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { row, col, color ->
                    board[row][col] = color
                },
                addSingleStone = { color, position -> Unit },
            )
        }
    }

    @Test
    fun `장목 조건일 때, 흑돌은 착수할 수 없다`() {
        assertThrows<IllegalArgumentException> {
            val board = createOverLineBoard()
            val blackBoard = Black(board)
            val position = Position.of(3, 'F')
            // when
            blackBoard.getWinningResult(
                position = position,
                markSinglePlace = { row, col, color ->
                    board[row][col] = color
                },
                addSingleStone = { color, position -> Unit },
            )
        }
    }
}
