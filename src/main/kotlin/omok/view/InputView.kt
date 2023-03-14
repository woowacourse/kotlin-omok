package omok.view

import omok.domain.OmokPoint
import omok.domain.XCoordinate
import omok.domain.YCoordinate

class InputView {
    fun inputPoint(name: String, omokPoint: OmokPoint?): OmokPoint {
        val lastLocation = omokPoint?.let { "(마지막 돌의 위치: ${it.x.value}${it.y.value})" } ?: ""
        println("${name}의 차례입니다. $lastLocation")
        print("위치를 입력하세요: ")
        return getPoint()
    }

    private fun getPoint(): OmokPoint = runCatching {
        readln().let {
            val x: Char = it[0].uppercaseChar()
            val y: Int = it.substring(1).toInt()
            return OmokPoint(XCoordinate(x), YCoordinate(y))
        }
    }.getOrElse { getPoint() }
}
