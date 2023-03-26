package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `금수인 경우 착수할 수 없다`() {
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[1][3] = State.BLACK
        board[2][3] = State.BLACK
        board[3][2] = State.BLACK
        board[3][4] = State.BLACK

        val omokGame = OmokGame(OmokBoard(board), FakeOmokListener())

        val actual = omokGame.successTurn2(Stone(3, 3), State.BLACK)

        assertThat(actual).isFalse
    }

    @Test
    fun `금수가 아닌경우 착수할 수 있다`() {
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }
        board[1][3] = State.BLACK
        board[2][3] = State.BLACK
        board[3][2] = State.BLACK

        val omokGame = OmokGame(OmokBoard(board), FakeOmokListener())

        val actual = omokGame.successTurn2(Stone(3, 3), State.BLACK)

        assertThat(actual).isTrue
    }
}
