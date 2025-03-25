package woowacourse.omok.domain

import woowacourse.omok.domain.grid.OmokGrid
import woowacourse.omok.domain.grid.OmokPoint
import woowacourse.omok.domain.rule.BlackRuleAdapterImpl
import woowacourse.omok.domain.rule.OmokRuleAdapter
import woowacourse.omok.domain.rule.WhiteRuleAdapterImpl

class OmokGame(val grid: OmokGrid) {
    private val referee = Referee()

    fun getStartingPlayer(): StoneColor {
        return StoneColor.BLACK
    }

    fun isBoardFull(): Boolean {
        return grid.isFull()
    }

    fun playMove(
        stoneColor: StoneColor,
        point: OmokPoint,
    ) {
        grid.putStone(point, stoneColor)
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
        referee.checkViolation(getRule(nowTurn), grid.getStones(StoneColor.BLACK), grid.getStones(StoneColor.WHITE), startPoint)
    }

    fun checkWin(
        nowTurn: StoneColor,
        startPoint: OmokPoint,
    ): Boolean {
        return referee.checkWin(getRule(nowTurn), grid.getStones(nowTurn), startPoint)
    }

    private fun getRule(nowTurn: StoneColor): OmokRuleAdapter {
        return when (nowTurn) {
            StoneColor.BLACK -> BlackRuleAdapterImpl
            StoneColor.WHITE -> WhiteRuleAdapterImpl
        }
    }
}
