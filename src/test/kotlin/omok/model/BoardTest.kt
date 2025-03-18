package omok.model

import omok.model.Board.Companion.customBoard
import omok.model.Board.Companion.initBoard
import org.assertj.core.api.Assertions.assertThat
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

        assertThrows<IllegalArgumentException> { nextBoard.placeStone(Stone(position55, StoneState.WHITE)) }
    }

    @Test
    fun `같은 색의 돌을 연속하여 착수할 수 없다`() {
        val initialBoard = initBoard()
        val position55 = Position(Row(5), Col(5))
        val position66 = Position(Row(6), Col(6))
        val nextBoard = initialBoard.placeStone(Stone(position55, StoneState.BLACK))

        assertThrows<IllegalArgumentException> { nextBoard.placeStone(Stone(position66, StoneState.BLACK)) }
    }

    @Test
    fun `대각선으로 오목임을 확인할 수 있다`() {
        val stones = List(5) { Stone(Position(Row(it), Col(it)), StoneState.WHITE) }

        val board = customBoard(stones)

        assertThat(board.isOmok(Position(Row(4), Col(4)))).isTrue()
    }

    @Test
    fun `세로로 오목임을 확인할 수 있다`() {
        val stones = List(5) { Stone(Position(Row(3), Col(it)), StoneState.WHITE) }

        val board = customBoard(stones)
        assertThat(board.isOmok(Position(Row(3), Col(4)))).isTrue()
    }

    @Test
    fun `가로로 오목임을 확인할 수 있다`() {
        val stones = List(5) { Stone(Position(Row(3), Col(it)), StoneState.WHITE) }

        val board = customBoard(stones)

        assertThat(board.isOmok(Position(Row(3), Col(4)))).isTrue()
    }

    @Test
    fun `6목 이상의 장목도 착수 가능하며 승리 조건으로 인정한다`() {
        val stones = List(6) { Stone(Position(Row(3), Col(it)), StoneState.WHITE) }

        val board = customBoard(stones)

        assertThat(board.isOmok(Position(Row(3), Col(5)))).isTrue()
    }
}
