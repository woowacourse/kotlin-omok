package woowacourse.omok.model

import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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
}
