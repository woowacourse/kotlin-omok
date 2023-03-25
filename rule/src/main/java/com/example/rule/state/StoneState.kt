package com.example.rule.state

import com.example.rule.ArkBoard
import com.example.rule.OmokPoint

interface StoneState {
    val korean: String
    fun next(): StoneState
    fun checkForbidden(arkBoard: ArkBoard, point: OmokPoint): Boolean
    fun validateWinner(arkBoard: ArkBoard, point: OmokPoint): Boolean
}
