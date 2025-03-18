package omok.model

import omok.model.Board.Companion.initBoard
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `돌을 원하는 위치에 착수할 수 있다`() {
        val initialBoard = initBoard()
        val position = Position(Row(5), Col(5))
        val stone = Stone(position, StoneState.BLACK)

        assertDoesNotThrow { initialBoard.placeStone(stone) }
    }

    @Test
    fun `돌이 놓여있는 위치에 착수할 수 없다`() {
        val initialBoard = initBoard()
        val position55 = Position(Row(5), Col(5))
        val nextBoard = initialBoard.placeStone(Stone(position55, StoneState.BLACK))

        assertThrows<IllegalStateException> { nextBoard.placeStone(Stone(position55, StoneState.WHITE)) }
    }

    @Test
    fun `같은 색의 돌을 연속하여 착수할 수 없다`() {
        val initialBoard = initBoard()
        val position55 = Position(Row(5), Col(5))
        val position66 = Position(Row(6), Col(6))
        val nextBoard = initialBoard.placeStone(Stone(position55, StoneState.BLACK))

        assertThrows<IllegalStateException> { nextBoard.placeStone(Stone(position66, StoneState.BLACK)) }
    }
}
