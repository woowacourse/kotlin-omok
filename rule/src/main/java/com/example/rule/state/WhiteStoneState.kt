package com.example.rule.state

import com.example.rule.ArkBoard
import com.example.rule.OmokPoint
import com.example.rule.Rule

object WhiteStoneState : StoneState {
    override val korean = "백"

    override fun next(): StoneState = BlackStoneState
    override fun checkForbidden(arkBoard: ArkBoard, point: OmokPoint): Boolean = true
    override fun validateWinner(arkBoard: ArkBoard, point: OmokPoint): Boolean {
        return Rule(arkBoard).validateWin(point)
    }
}
