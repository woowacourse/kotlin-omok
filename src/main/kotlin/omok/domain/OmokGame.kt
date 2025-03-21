package omok.domain

import omok.domain.point.OmokPoint
import omok.domain.rule.BlackRuleAdapterImpl
import omok.domain.rule.OmokRuleAdapter
import omok.domain.rule.Referee
import omok.domain.rule.WhiteRuleAdapterImpl

class OmokGame(val grid: OmokGrid) {
    private val referee = Referee()

    fun getStartingPlayer(): StoneState {
        return StoneState.BLACK
    }

    fun isBoardFull(): Boolean {
        return grid.isFull()
    }

    fun playMove(
        stoneColor: StoneState,
        point: OmokPoint,
    ) {
        grid.putStone(point, stoneColor)
    }

    fun getOtherPlayer(turn: StoneState): StoneState {
        return if (turn == StoneState.BLACK) {
            StoneState.WHITE
        } else {
            StoneState.BLACK
        }
    }

    fun validatePoint(
        stoneColor: StoneState,
        point: OmokPoint,
    ) {
        referee.checkViolation(getRule(stoneColor), grid, point)
    }

    fun checkWin(
        nowTurn: StoneState,
        thisTurnPoint: OmokPoint,
    ): Boolean {
        return referee.checkWin(getRule(nowTurn), getStones(nowTurn), thisTurnPoint)
    }

    fun getRule(stoneColor: StoneState): OmokRuleAdapter {
        return when (stoneColor) {
            StoneState.WHITE -> WhiteRuleAdapterImpl
            StoneState.BLACK -> BlackRuleAdapterImpl
        }
    }

    fun getStones(nowTurn: StoneState): Set<OmokPoint> {
        return when (nowTurn) {
            StoneState.BLACK -> grid.blackStones.stones
            StoneState.WHITE -> grid.whiteStones.stones
        }
    }
}
