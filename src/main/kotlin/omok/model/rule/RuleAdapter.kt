package omok.model.rule

import omok.model.Board
import omok.model.Position
import omok.model.Stone
import omok.model.rule.library.FourFourRule
import omok.model.rule.library.OverlineRule
import omok.model.rule.library.ThreeThreeRule
import omok.model.rule.winning.ContinualStonesWinningCondition

class RuleAdapter2(
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

object RuleAdapter {
    fun abideDoubleFourRule(
        stone: Stone,
        board: Board,
        position: Position,
    ): Boolean {
        return FourFourRule(stone.convert(), stone.next().convert()).abide(board.convert(), position.convert())
    }

    fun abideDoubleOpenThreeRule(
        stone: Stone,
        board: Board,
        position: Position,
    ): Boolean {
        return ThreeThreeRule(stone.convert(), stone.next().convert()).abide(board.convert(), position.convert())
    }

    fun abideOverLineRule(
        continualStonesStandard: ContinualStonesStandard,
        board: Board,
        position: Position,
    ): Boolean {
        return OverlineRule(continualStonesStandard.count).abide(board.convert(), position.convert())
    }

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
