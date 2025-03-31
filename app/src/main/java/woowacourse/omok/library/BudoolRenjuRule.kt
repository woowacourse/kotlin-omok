package woowacourse.omok.library

import rule.facade.BlackRenjuRule
import woowacourse.omok.domain.Board
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.RenjuRule
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneType

class BudoolRenjuRule : RenjuRule {
    private val rule: BlackRenjuRule = BlackRenjuRule(15, 15)

    private fun Position.toPair() = x to y

    private fun Stone.toPair() = position.x to position.y

    override fun checkWin(
        board: Board,
        position: Position,
    ): Boolean {
        val blackStones =
            board.stones
                .filter { it.color == StoneType.BLACK }
                .map { it.position.toPair() }
        val whiteStones =
            board.stones
                .filter { it.color == StoneType.WHITE }
                .map { it.position.toPair() }
        return rule.checkWin(
            blackPoints = blackStones,
            whitePoints = whiteStones,
            startPoint = position.toPair(),
            stoneStandardCount = 5,
        )
    }

    override fun checkDoubleFourFoul(
        board: Board,
        position: Position,
    ): Boolean {
        val blackPairs =
            board.stones
                .filter { it.color == StoneType.BLACK }
                .map { it.toPair() }
        val whitePairs =
            board.stones
                .filter { it.color == StoneType.WHITE }
                .map { it.toPair() }
        return rule.checkDoubleFourFoul(
            blackPoints = blackPairs,
            whitePoints = whitePairs,
            startPoint = position.toPair(),
        )
    }

    override fun checkDoubleThreeFoul(
        board: Board,
        position: Position,
    ): Boolean {
        val blackPairs =
            board.stones
                .filter { it.color == StoneType.BLACK }
                .map { it.toPair() }
        val whitePairs =
            board.stones
                .filter { it.color == StoneType.WHITE }
                .map { it.toPair() }
        return rule.checkDoubleThreeFoul(
            blackPoints = blackPairs,
            whitePoints = whitePairs,
            startPoint = position.toPair(),
        )
    }

    override fun checkOverline(
        board: Board,
        position: Position,
    ): Boolean {
        val blackPairs =
            board.stones
                .filter { it.color == StoneType.BLACK }
                .map { it.toPair() }
        return rule.checkOverline(
            stonesPoints = blackPairs,
            startPoint = position.toPair(),
        )
    }
}
