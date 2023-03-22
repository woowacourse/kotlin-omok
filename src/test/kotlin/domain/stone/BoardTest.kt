package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class BoardTest {

    @Test
    fun `board에 스톤을 추가 할 수 있다`() {
        val board = Board()
        board.putStone(Stone(StonePosition.from(1, 1), StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(3, 1), StoneType.WHITE))

        assertAll(
            { assertThat(board.board[1][1]).isEqualTo(StoneType.BLACK) },
            { assertThat(board.board[1][3]).isEqualTo(StoneType.WHITE) },
            { assertThat(board.stones.values.size).isEqualTo(2) },
        )
    }
}
