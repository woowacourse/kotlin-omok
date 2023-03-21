package omok.domain.omokRule.adapter

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.state.StoneState

interface Rule {
    fun isForbidden(omokBoard: OmokBoard, omokPoint: OmokPoint): Boolean
    fun isWin(omokBoard: OmokBoard, omokPoint: OmokPoint, stoneState: StoneState): Boolean
}
