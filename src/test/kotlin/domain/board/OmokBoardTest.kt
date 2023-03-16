package domain.board

import domain.State
import domain.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokBoardTest {
    @Test
    fun `흑돌을 2, 3에 착수하면 오목판의 2, 3 위치가 Black이다`() {
        val omokBoard = OmokBoard()
        omokBoard.move(Stone(2, 3), State.BLACK)

        assertThat(omokBoard.board.value[2][3]).isEqualTo(State.BLACK)
    }

    @Test
    fun `흑돌을 2, 2에 착수하면 오목판의 2, 2 위치가 White다`() {
        val omokBoard = OmokBoard()
        omokBoard.move(Stone(2, 2), State.WHITE)

        assertThat(omokBoard.board.value[2][2]).isEqualTo(State.WHITE)
    }
}
