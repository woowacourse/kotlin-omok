package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `오목판은 판 위에 놓인 돌을 가지고 있다`() {
        val board = Board(
            listOf(
                Stone("black", Coordinate(5, "A")),
                Stone("white", Coordinate(8, "H")),
            )
        )

        assertThat(board.stones.size).isEqualTo(2)
    }
}
