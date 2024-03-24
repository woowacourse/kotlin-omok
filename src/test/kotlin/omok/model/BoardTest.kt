package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `이미 돌이 있는 자리에 착수를 진행하면, 예외를 발생시킬 수 있어야 한다`() {
        // given
        val stones =
            listOf(
                Stone.Black(Position(Row.ONE, Column.A)),
                Stone.White(Position(Row.TEN, Column.C)),
            )
        val board = Board(stones)
        assertThrows<IllegalArgumentException> {
            board.place(Position(Row.ONE, Column.A))
        }
    }

    @Test
    fun `플레이어가 번갈아가며 착수할 수 있어야 한다`() {
        // given
        val board = Board()
        // when
        board.place(Position(Row.TEN, Column.B))
        board.place(Position(Row.ONE, Column.A))
        // then
        assertThat(board.stones.last().color).isEqualTo(Color.WHITE)
    }
}
