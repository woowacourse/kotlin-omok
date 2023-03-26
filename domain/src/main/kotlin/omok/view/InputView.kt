package omok.view

import omok.domain.OmokPoint
import omok.domain.XCoordinate
import omok.domain.YCoordinate
import omok.domain.state.StoneState

object InputView {
    fun inputPoint(stoneState: StoneState, omokPoint: OmokPoint?): OmokPoint {
        val lastLocation = omokPoint?.let { "(마지막 돌의 위치: ${it.x.toChar()}${it.y.value})" } ?: ""
        println("${stoneState.toUiModel().text}의 차례입니다. $lastLocation")
        print("위치를 입력하세요: ")
        return readPoint()
    }

    private fun readPoint(): OmokPoint = runCatching {
        readln().let {
            val x: Char = it[0].uppercaseChar()
            val y: Int = it.substring(1).toInt()
            return OmokPoint(XCoordinate(x), YCoordinate(y))
        }
    }.onFailure { println(it.message) }
        .getOrElse { readPoint() }
}
