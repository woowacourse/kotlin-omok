package woowacourse.omok.model.rule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.position.Col
import woowacourse.omok.model.stone.position.Position
import woowacourse.omok.model.stone.position.Row
import java.util.LinkedHashMap

class BudoolRenjuRuleAdapterTest {
    @Test
    fun `부둘렌주룰 라이브러리를 가져와 보드의 마지막 돌이 금수인지 판단할 수 있다`() {
        val stones: LinkedHashMap<Position, StoneColor> = LinkedHashMap()
        val coordination: List<Pair<Row, Col>> =
            listOf(
                Row(6) to Col(4),
                Row(8) to Col(4),
                Row(7) to Col(5),
                Row(7) to Col(6),
                Row(7) to Col(4),
            )
        coordination.forEach {
            stones[Position(it.first, it.second)] = StoneColor.BLACK
        }
        val lastStone = Stone(Position(Row(7), Col(4)), StoneColor.BLACK)

        val actualFoul = BudoolRenjuRuleAdapter(BoardSize()).checkLastBlackStoneFoul(stones, lastStone)
        assertThat(actualFoul).isEqualTo(RenjuFoul.THREE_BY_THREE_FOUL)
    }

    @Test
    fun `보드의 마지막 돌이 오목을 만들었는지 판단할 수 있다`() {
        val stones: LinkedHashMap<Position, StoneColor> = LinkedHashMap()
        val coordination: List<Pair<Row, Col>> =
            listOf(
                Row(6) to Col(4),
                Row(7) to Col(4),
                Row(8) to Col(4),
                Row(9) to Col(4),
                Row(10) to Col(4),
            )
        coordination.forEach {
            stones[Position(it.first, it.second)] = StoneColor.BLACK
        }
        val board = Board(stonesMap = stones)
        val lastStone = Stone(Position(coordination.last().first, coordination.last().second), StoneColor.BLACK)

        val actualOmok = BudoolRenjuRuleAdapter(board.boardSize).isOmok(board.stonesMap, lastStone)
        assertThat(actualOmok).isTrue()
    }
}
