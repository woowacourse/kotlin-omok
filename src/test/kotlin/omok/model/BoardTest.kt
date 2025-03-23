package omok.model

import omok.model.board.Board.Companion.initBoard
import omok.model.stone.StoneColor
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `빈 위치에 돌을 두면 새로운 보드에 포함된다`() {
        val board = initBoard()
        val position = Position(Row(0), Col(0))

        val newBoard = board.positionAt(position, StoneColor.BLACK)

        assertThat(newBoard.stonesMap[position]).isEqualTo(StoneColor.BLACK)
    }

    @Test
    fun `이미 돌이 있는 위치에 돌을 놓을 수 없다`() {
        val position = Position(Row(0), Col(0))
        val board = initBoard().positionAt(position, StoneColor.BLACK)

        assertThrows<IllegalArgumentException> { board.positionAt(position, StoneColor.WHITE) }
    }
}
