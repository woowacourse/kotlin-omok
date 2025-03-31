package woowacourse.omok.model

import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import woowacourse.omok.model.board.Board.Companion.initBoard
import woowacourse.omok.model.board.BoardDimensions
import woowacourse.omok.model.stone.StoneColor

class BoardTest {
    private val board = initBoard(BoardDimensions(15, 15))

    @Test
    fun `빈 위치에 돌을 두면 새로운 보드에 포함된다`() {
        val position = Position(Row(0), Col(0))
        val newBoard = board.placeStone(position, StoneColor.BLACK)

        assertThat(newBoard.stonesMap[position]).isEqualTo(StoneColor.BLACK)
    }

    @Test
    fun `흑돌의 리스트를 확인할 수 있다`() {
        val position1 = Position(Row(0), Col(0))
        val position2 = Position(Row(1), Col(2))
        val position3 = Position(Row(4), Col(5))
        val position4 = Position(Row(10), Col(10))
        val position5 = Position(Row(11), Col(11))
        var newBoard = initBoard(BoardDimensions(15, 15))

        val positions = listOf<Position>(position1, position2, position3, position4)
        for (position in positions) {
            newBoard = newBoard.placeStone(position, StoneColor.BLACK)
        }

        newBoard = newBoard.placeStone(position5, StoneColor.WHITE)

        assertAll(
            { assertThat(newBoard.getBlackStones()).containsAll(positions) },
            { assertThat(newBoard.getBlackStones().size).isEqualTo(4) },
        )
    }

    @Test
    fun `착수된 흰돌의 리스트를 확인할 수 있다`() {
        val position1 = Position(Row(0), Col(0))
        val position2 = Position(Row(1), Col(2))
        val position3 = Position(Row(5), Col(5))
        val position4 = Position(Row(0), Col(10))
        val position5 = Position(Row(1), Col(11))
        var newBoard = initBoard(BoardDimensions(15, 15))

        val positions = listOf<Position>(position1, position2, position3, position4, position5)
        for (position in positions) {
            newBoard = newBoard.placeStone(position, StoneColor.WHITE)
        }

        assertAll(
            { assertThat(newBoard.getWhiteStones()).containsAll(positions) },
            { assertThat(newBoard.getWhiteStones().size).isEqualTo(5) },
        )
    }
}
