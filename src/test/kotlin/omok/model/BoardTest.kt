package omok.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `이미 돌이 있는 자리에 착수를 진행하면, 예외를 발생시킨다`() {
        // given
        val stones =
            listOf(
                Stone(Color.BLACK, Position(Row.ONE, Column.A)),
                Stone(Color.WHITE, Position(Row.TEN, Column.C)),
            )
        val board = Board(stones)
        assertThrows<IllegalArgumentException> {
            board.place(Stone(Color.BLACK, Position(Row.ONE, Column.A)))
        }
    }
}
