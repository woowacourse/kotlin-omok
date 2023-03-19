package domain.rule.state

import domain.rule.ArkBoard
import domain.rule.OmokPoint

interface StoneState {
    val korean: String
    fun next(): StoneState
    fun checkForbidden(arkBoard: ArkBoard, point: OmokPoint): Boolean
    fun validateWinner(arkBoard: ArkBoard, point: OmokPoint): Boolean
}
