package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {

    @Test
    fun `흑돌이 오목을 완성하면 흑이 승리한다`() {

        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }

        board[0][0] = State.BLACK
        board[0][1] = State.BLACK
        board[0][2] = State.BLACK
        board[0][3] = State.BLACK
        board[0][4] = State.BLACK

        val omokGame = OmokGame(
            omokBoard = OmokBoard(board),
            omokGameListener = listener()
        )

        val actual = omokGame.isVictory(State.BLACK)
        assertThat(actual).isEqualTo(true)
    }

    @Test
    internal fun `금수인 경우 착수할 수 없다`() {
        val board =
            MutableList(OmokBoard.BOARD_SIZE) { MutableList(OmokBoard.BOARD_SIZE) { State.EMPTY } }

        board[5][5] = State.BLACK
        board[4][5] = State.BLACK
        board[3][4] = State.BLACK
        board[3][6] = State.BLACK

        val omokGame = OmokGame(
            omokBoard = OmokBoard(board),
            omokGameListener = listener()
        )

        val actual = omokGame.successTurn(Stone(3, 5), State.BLACK)
        assertThat(actual).isEqualTo(false)
    }

    private fun listener() = object : OmokGameListener {

        override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {}

        override fun onMoveFail() {}

        override fun onForbidden() {}

        override fun onFinish(state: State) {}
    }
}
