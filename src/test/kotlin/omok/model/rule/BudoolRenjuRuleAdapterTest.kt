package omok.model.rule

import omok.model.board.Board
import omok.model.stone.StoneColor
import omok.model.stone.position.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.LinkedHashMap

class BudoolRenjuRuleAdapterTest {
    @Test
    fun `부둘렌주룰 라이브러리를 가져와 보드의 마지막 돌이 금수인지 판단할 수 있다`() {
        val stones: LinkedHashMap<Position, StoneColor> = LinkedHashMap()
        val coordination = listOf("G5", "I5", "H6", "H7", "H5")
        coordination.forEach {
            stones[Position(it)] = StoneColor.BLACK
        }
        val board = Board(stonesMap = stones)

        val actualFoul = BudoolRenjuRuleAdapter(board.boardSize).checkLastBlackStoneFoul(board)
        assertThat(actualFoul).isEqualTo(RenjuFoul.THREE_BY_THREE_FOUL)
    }

    @Test
    fun `보드의 마지막 돌이 오목인지 판단할 수 있다`() {
        val stones: LinkedHashMap<Position, StoneColor> = LinkedHashMap()
        val coordination = listOf("G5", "G6", "G7", "G8", "G9")
        coordination.forEach {
            stones[Position(it)] = StoneColor.BLACK
        }
        val board = Board(stonesMap = stones)

        val actualOmok = BudoolRenjuRuleAdapter(board.boardSize).isLastStoneOmok(board)
        assertThat(actualOmok).isTrue()
    }
}
