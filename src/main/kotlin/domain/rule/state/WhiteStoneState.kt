package domain.rule.state

import domain.rule.ArkBoard
import domain.rule.OmokPoint
import domain.rule.Rule

object WhiteStoneState : StoneState {
    override val korean = "백"

    override fun next(): StoneState = BlackStoneState
    override fun checkForbidden(arkBoard: ArkBoard, point: OmokPoint): Boolean = true
    override fun validateWinner(arkBoard: ArkBoard, point: OmokPoint): Boolean {
        return Rule(arkBoard).validateWin(point)
    }
}
