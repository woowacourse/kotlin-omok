package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.OmokRule

object WhiteStoneState : StoneState {
    override val korean = "백"

    override fun next(): StoneState = BlackStoneState
    override fun checkForbidden(omokBoard: OmokBoard, point: OmokPoint): Boolean = true
    override fun validateWinner(omokBoard: OmokBoard, point: OmokPoint): Boolean {
        return OmokRule(omokBoard, this).validateWin(point)
    }
}
