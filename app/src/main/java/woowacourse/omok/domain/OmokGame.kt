package woowacourse.omok.domain

import woowacourse.omok.domain.StoneColor.Companion.opposite
import woowacourse.omok.domain.grid.OmokGrid
import woowacourse.omok.domain.grid.OmokPoint
import woowacourse.omok.domain.rule.RenjuRuleAdapterImpl
import woowacourse.omok.domain.rule.ValidationResult

class OmokGame(val grid: OmokGrid) {
    private val referee = Referee()

    fun getStartingPlayer(): StoneColor {
        val blackStoneCount = grid.getStonesByColor(StoneColor.BLACK).size
        val whiteStoneCount = grid.getStonesByColor(StoneColor.WHITE).size
        if (blackStoneCount == whiteStoneCount) return StoneColor.BLACK
        return StoneColor.WHITE
    }

    fun playMove(point: OmokPoint) {
        grid.putStone(point)
    }

    fun changeTurn(nowTurn: StoneColor): StoneColor {
        return if (nowTurn == StoneColor.BLACK) {
            StoneColor.WHITE
        } else {
            StoneColor.BLACK
        }
    }

    fun validatePoint(
        nowTurn: StoneColor,
        startPoint: OmokPoint,
    ): ValidationResult {
        val thisStones = grid.getStonesByColor(nowTurn)
        val opponentStones = grid.getStonesByColor(opposite(nowTurn))

        return referee.checkViolation(
            RenjuRuleAdapterImpl,
            thisStones,
            opponentStones,
            startPoint,
        )
    }

    fun checkWin(
        nowTurn: StoneColor,
        startPoint: OmokPoint,
    ): Boolean {
        return referee.checkWin(RenjuRuleAdapterImpl, grid.getStonesByColor(nowTurn), startPoint)
    }

    fun isBoardFull(): Boolean {
        return grid.isFull()
    }
}
