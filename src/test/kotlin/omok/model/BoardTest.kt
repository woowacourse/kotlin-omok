package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    private lateinit var board: Board
    private lateinit var blackStone: Stone
    private lateinit var whiteStone: Stone
    private lateinit var coordinate: Coordinate

    @BeforeEach
    fun setUp() {
        board = Board(Stones())
        coordinate = Coordinate(Row.from("8"), Column.from("F"))
        blackStone = Stone(black, coordinate)
        whiteStone = Stone(white, coordinate)
    }

    @Test
    fun `오목판은 착수된 돌을 가지고 있다`() {
        board.putStone(blackStone)
        assertThat(board.stones.stones).contains(blackStone)
    }

    @Test
    fun `흑 플레이어가 3-3을 만드는 경우, 착수할 수 없다`() {
        samsamStones.forEach { stone -> board.putStone(stone) }
        assertThrows<IllegalStateException> {
            board.putStone(blackStone)
        }
    }

    @Test
    fun `흑 플레이어가 4-4을 만드는 경우, 착수할 수 없다`() {
        fourfourStones.forEach { stone -> board.putStone(stone) }
        assertThrows<IllegalStateException> {
            board.putStone(blackStone)
        }
    }

    @Test
    fun `흑 플레이어가 장목을 만드는 경우, 착수할 수 없다`() {
        moreThanFiveStones.forEach { stone -> board.putStone(stone) }
        assertThrows<IllegalStateException> {
            board.putStone(blackStone)
        }
    }

    @Test
    fun `흑 플레이어가 열린 4-4을 만드는 경우, 착수할 수 없다`() {
        openFourFourStones.forEach { stone -> board.putStone(stone) }
        assertThrows<IllegalStateException> {
            board.putStone(blackStone)
        }
    }
}
