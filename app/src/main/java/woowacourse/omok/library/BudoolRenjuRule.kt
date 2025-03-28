package woowacourse.omok.library

import rule.facade.BlackRenjuRule
import woowacourse.omok.domain.Board
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.RenjuRule
import woowacourse.omok.domain.StoneType

class BudoolRenjuRule : RenjuRule {
    private val rule: BlackRenjuRule = BlackRenjuRule(15, 15)

    override fun checkWin(
        board: Board,
        position: Position,
    ): Boolean {
        val stones = board.stones
        val blackStones = stones.filter { it.color == StoneType.BLACK }
        val whiteStones = stones.filter { it.color == StoneType.WHITE }
        val count = stones.count { it.color != StoneType.WHITE }
        return rule.checkWin(
            blackStones.map { it.position.x to it.position.y },
            whiteStones.map { it.position.x to it.position.y },
            position.x to position.y,
            count,
        )
    }

    override fun checkDoubleFourFoul(
        board: Board,
        position: Position,
    ): Boolean {
        val stones = board.stones
        val blackStones = stones.filter { it.color == StoneType.BLACK }
        val whiteStones = stones.filter { it.color == StoneType.WHITE }
        return true
    }

    override fun checkDoubleThreeFoul(
        board: Board,
        position: Position,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun checkOverline(
        board: Board,
        position: Position,
    ): Boolean {
        TODO("Not yet implemented")
    }
}
