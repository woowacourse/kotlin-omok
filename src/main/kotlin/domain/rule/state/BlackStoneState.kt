package domain.rule.state

import domain.rule.ArkBoard
import domain.rule.OmokPoint
import domain.rule.Rule

object BlackStoneState : StoneState {
    override val korean = "흑"

    override fun next(): StoneState = WhiteStoneState

    override fun checkForbidden(arkBoard: ArkBoard, point: OmokPoint): Boolean {
        return Rule(arkBoard).countOpenThrees(point) <= 1 && Rule(arkBoard).countOpenFours(point) <= 1
    }

    override fun validateWinner(arkBoard: ArkBoard, point: OmokPoint): Boolean {
        return Rule(arkBoard).validateWin(point)
    }
}
