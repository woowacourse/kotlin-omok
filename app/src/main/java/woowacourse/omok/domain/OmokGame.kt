package woowacourse.omok.domain

import woowacourse.omok.domain.grid.OmokGrid
import woowacourse.omok.domain.grid.OmokPoint
import woowacourse.omok.domain.rule.RenjuRuleAdapterImpl

class OmokGame(val grid: OmokGrid) {
    private val referee = Referee()

    fun getStartingPlayer(): StoneColor {
        return StoneColor.BLACK
    }

    fun isBoardFull(): Boolean {
        return grid.isFull()
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
    ) {
        if (nowTurn == StoneColor.BLACK) {
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStones(StoneColor.BLACK),
                grid.getStones(StoneColor.WHITE),
                startPoint,
            )
        } else {
            referee.checkViolation(
                RenjuRuleAdapterImpl,
                grid.getStones(StoneColor.WHITE),
                grid.getStones(StoneColor.BLACK),
                startPoint,
            )
        }
    }

    fun checkWin(
        nowTurn: StoneColor,
        startPoint: OmokPoint,
    ): Boolean {
        return referee.checkWin(RenjuRuleAdapterImpl, grid.getStones(nowTurn), startPoint)
    }
}
