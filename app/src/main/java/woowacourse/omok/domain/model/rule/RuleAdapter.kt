package woowacourse.omok.domain.model.rule

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.rule.library.OverlineRule

class RuleAdapter(
    private val baseOverLineRule: OverlineRule,
    private val forbiddenRules: ForbiddenRules,
) {
    init {
        if (!baseOverLineRule.canHaveDoubleRule()) {
            require(!forbiddenRules.hasDoubleRule()) { "승리 조건이 오목 미만일 경우에는 더블 규칙(3-3, 4-4)을 가질 수 없습니다." }
        }
    }

    fun violated(
        board: Board,
        position: Position,
    ): Boolean {
        if (!baseOverLineRule.abide(board.convert(), position.convert())) {
            return true
        }

        return forbiddenRules.rules.any { !it.abide(board.convert(), position.convert()) }
    }

    fun isWin(
        board: Board,
        position: Position,
    ): Boolean = baseOverLineRule.isWin(board.convert(), position.convert())

    private fun Board.convert(): List<List<Int>> {
        val array = Array(15) { IntArray(15) }
        board.forEach { (position, stone) ->
            array[position.col][position.row] =
                stone.convert()
        }
        return array.map { it.toList() }.toList()
    }

    private fun Stone.convert(): Int =
        when (this) {
            Stone.BLACK -> 1
            Stone.WHITE -> 2
            Stone.NONE -> 0
        }

    private fun Position.convert(): Pair<Int, Int> = row to col
}
