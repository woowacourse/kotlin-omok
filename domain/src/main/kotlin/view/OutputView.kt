package view

import domain.point.Point
import domain.stone.StoneColor

interface OutputView {
    fun startGame()
    fun drawPoint(stoneColor: StoneColor, newPoint: Point)
    fun printPutFailed()
    fun printResult(lastStoneColor: StoneColor, winnerStoneColor: StoneColor, newPoint: Point)
    fun printTurnStartMessage(stoneColor: StoneColor, point: Point?)
}
