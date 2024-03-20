package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var board: Board
    private lateinit var blackStone: Stone
    private lateinit var whiteStone: Stone
    private lateinit var coordinate: Coordinate

    @BeforeEach
    fun setUp() {
        board = Board(Stones())
        coordinate = Coordinate(Row.from("6"), Column.from("C"))
        blackStone = Stone(Color.BLACK, coordinate)
        whiteStone = Stone(Color.WHITE, coordinate)
    }

    @Test
    fun `오목판은 착수된 돌을 가지고 있다`() {
        board.putStone(blackStone)
        assertThat(board.stones.stones).contains(blackStone)
    }
}
