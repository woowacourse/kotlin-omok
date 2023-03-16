package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.OmokRule

object BlackStoneState : StoneState {
    override val korean = "흑"

    override fun next(): StoneState = WhiteStoneState

    override fun checkForbidden(omokBoard: OmokBoard, point: OmokPoint): Boolean {
        return OmokRule(omokBoard).countOpenThrees(point) <= 1 && OmokRule(omokBoard).countOpenFours(point) <= 1
    }

    override fun validateWinner(omokBoard: OmokBoard, point: OmokPoint): Boolean {
        return OmokRule(omokBoard).validateWin(point)
    }
}
