package omok.omokRule.adapter

import omok.OmokBoard
import omok.OmokPoint
import omok.state.StoneState

interface WinRule {
    fun isWin(omokBoard: OmokBoard, omokPoint: OmokPoint, stoneState: StoneState): Boolean
}
