package omok.model

import omok.model.board.Board
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `돌을 원하는 위치에 착수할 수 있다`() {
        val initialBoard = Board()
        val position = Position(Row(5), Col(5))

        assertDoesNotThrow { initialBoard.placeStone(position) }
    }

    @Test
    fun `돌이 놓여있는 위치에 착수할 수 없다`() {
        val initialBoard = Board()
        val position55 = Position(Row(5), Col(5))
        val nextBoard = initialBoard.placeStone(position55)

        assertThrows<IllegalArgumentException> { nextBoard.placeStone(position55) }
    }
}
