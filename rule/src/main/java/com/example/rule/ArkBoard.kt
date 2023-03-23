package com.example.rule

import com.example.rule.state.BlackStoneState
import com.example.rule.state.StoneState

class ArkBoard(
    val value: Map<YCoordinate, OmokLine>,
    val stoneState: StoneState = BlackStoneState,
) {

    val keys = value.keys
    val values = value.values

    fun placeStone(point: OmokPoint, stoneState: StoneState = this.stoneState): ArkBoard {
        val newValue = value.toMutableMap()
        newValue[point.y] = newValue[point.y]?.placeStone(point, stoneState) ?: throw IllegalArgumentException()
        return ArkBoard(newValue, stoneState.next())
    }

    operator fun get(yCoordinate: YCoordinate): OmokLine = value[yCoordinate] ?: throw IllegalArgumentException()

    constructor () : this(YCoordinate.all().associateWith { OmokLine() })
}
