package omok.model.board

import omok.model.stone.Stone
import omok.model.stone.StoneState
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `돌을 원하는 위치에 착수할 수 있다`() {
        val initialBoard = Board()
        val position = Position(Row(5), Col(5))
        val nextBoard = initialBoard.nextStonePlacedBoard(position)

        assertThat(nextBoard.lastStone).isEqualTo(Stone(position, StoneState.BLACK))
    }

    @Test
    fun `보드의 범위를 벗어난 위치에 착수할 수 없다`() {
        val initialBoard = Board(BoardSize(10))
        val position1515 = Position(Row(10), Col(10))

        assertThrows<IllegalArgumentException> { initialBoard.nextStonePlacedBoard(position1515) }
    }

    @Test
    fun `돌이 놓여있는 위치에 착수할 수 없다`() {
        val initialBoard = Board()
        val position55 = Position(Row(5), Col(5))
        val nextBoard = initialBoard.nextStonePlacedBoard(position55)

        assertThrows<IllegalArgumentException> { nextBoard.nextStonePlacedBoard(position55) }
    }
}
