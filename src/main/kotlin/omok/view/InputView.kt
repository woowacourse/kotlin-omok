package omok.view

import omok.domain.OmokPoint
import omok.domain.XCoordinate
import omok.domain.YCoordinate
import omok.domain.state.BlackStoneState
import omok.domain.state.StoneState
import omok.domain.state.WhiteStoneState

class InputView {
    private fun StoneState.name(): String = when (this) {
        BlackStoneState -> "흑"
        WhiteStoneState -> "백"
        else -> ""
    }

    fun inputPoint(stoneState: StoneState, omokPoint: OmokPoint?): OmokPoint {
        val lastLocation = omokPoint?.let { "(마지막 돌의 위치: ${it.x.toChar()}${it.y.value})" } ?: ""
        println("${stoneState.name()}의 차례입니다. $lastLocation")
        print("위치를 입력하세요: ")
        return getPoint()
    }

    private fun getPoint(): OmokPoint = runCatching {
        readln().let {
            val x: Char = it[0].uppercaseChar()
            val y: Int = it.substring(1).toInt()
            return OmokPoint(XCoordinate(x), YCoordinate(y))
        }
    }.onFailure { println(it.message) }
        .getOrElse { getPoint() }
}
