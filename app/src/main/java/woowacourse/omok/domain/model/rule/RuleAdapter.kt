package woowacourse.omok.domain.model.rule

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.rule.library.NoneForbiddenRule
import woowacourse.omok.domain.model.rule.library.OmokRule
import woowacourse.omok.domain.model.rule.winning.ContinualStonesWinningCondition

class RuleAdapter(
    private val continualStonesWinningCondition: ContinualStonesWinningCondition,
    rules: ForbiddenRules,
) {
    private val forbiddenRules: ForbiddenRules =
        rules.initOverlineStandard(continualStonesWinningCondition.continualStonesStandard)

    init {
        if (!continualStonesWinningCondition.canHaveDoubleRule()) {
            require(!forbiddenRules.hasDoubleRule()) { "승리 조건이 오목 미만일 경우에는 더블 규칙(3-3, 4-4)을 가질 수 없습니다." }
        }
        if (!continualStonesWinningCondition.canHaveOverlineRule()) {
            require(!forbiddenRules.hasOverlineRule()) { "승리 조건이 'N목 이상 완성' 일 경우에는 장목 규칙을 가질 수 없습니다." }
        }
    }

    fun validPosition(
        board: Board,
        position: Position,
    ): Boolean {
        if (forbiddenRules.hasNoRule()) return true
        return forbiddenRules.rules.all { it.abide(board.convert(), position.convert()) }
    }

    fun violatedRule(
        board: Board,
        position: Position,
    ): OmokRule = forbiddenRules.rules.find { !it.abide(board.convert(), position.convert()) }
        ?: NoneForbiddenRule

    fun violated(board: Board, position: Position): Boolean =
        forbiddenRules.rules.any{!it.abide(board.convert(), position.convert())}

    fun isWin(
        board: Board,
        position: Position,
    ): Boolean = continualStonesWinningCondition.isWin(board, position)

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
