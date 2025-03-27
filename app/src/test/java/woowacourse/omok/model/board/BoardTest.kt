package woowacourse.omok.model.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.position.Col
import woowacourse.omok.model.stone.position.Position
import woowacourse.omok.model.stone.position.Row

class BoardTest {
    @ParameterizedTest
    @CsvSource(
        "14, 14, EMPTY",
        "0, 0, EMPTY",
        "15, 15, OUT_OF_RANGE",
        "-1, -1, OUT_OF_RANGE",
        "5, 5, PLACED",
    )
    fun `보드의 특정 포지션의 상태를 조회할 수 있다`(
        row: Int,
        col: Int,
        expectedStatus: PositionStatus,
    ) {
        val board = Board(stonesMap = LinkedHashMap(mapOf(Position(Row(5), Col(5)) to StoneColor.BLACK)))
        val checkStonePosition = Position(Row(row), Col(col))

        assertThat(board.positionStatus(checkStonePosition)).isEqualTo(expectedStatus)
    }

    @Test
    fun `돌을 원하는 위치에 착수할 수 있다`() {
        val initialBoard = Board()
        val position = Position(Row(5), Col(5))
        val nextBoard = initialBoard.nextStonePlacedBoard(position)

        assertThat(nextBoard.lastStone).isEqualTo(Stone(position, StoneColor.BLACK))
    }

    @Test
    fun `다음 착수할 돌의 색상을 알려준다`() {
        val initialBoard = Board()
        assertThat(initialBoard.nextStoneColor).isEqualTo(StoneColor.BLACK)

        val position = Position(Row(5), Col(5))
        val nextBoard = initialBoard.nextStonePlacedBoard(position)

        assertThat(nextBoard.nextStoneColor).isEqualTo(StoneColor.WHITE)
    }

    @Test
    fun `착수한 돌의 정보를 복사하여 줄 수 있다`() {
        val stones: LinkedHashMap<Position, StoneColor> = LinkedHashMap()
        val coordination: List<Pair<Row, Col>> =
            listOf(
                Row(6) to Col(4),
                Row(8) to Col(4),
                Row(7) to Col(5),
            )
        coordination.forEach {
            stones[Position(it.first, it.second)] = StoneColor.BLACK
        }

        val board = Board(stonesMap = stones)
        assertThat(board.stonesMap).isEqualTo(stones)
        assertThat(board.stonesMap).isNotSameAs(stones)
    }
}
