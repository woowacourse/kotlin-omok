package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.OmokPoint

interface StoneState {
    val korean: String
    fun next(): StoneState
    fun checkForbidden(omokBoard: OmokBoard, point: OmokPoint): Boolean
    fun validateWinner(omokBoard: OmokBoard, point: OmokPoint): Boolean
}
