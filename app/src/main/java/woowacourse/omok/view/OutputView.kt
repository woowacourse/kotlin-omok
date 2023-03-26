package woowacourse.omok.view

import domain.point.Point
import domain.stone.StoneColor

interface OutputView {
    fun startGame()
    fun drawStone(lastStoneColor: StoneColor, newPoint: Point)
    fun showPutFailed()
    fun showResult(lastStoneColor: StoneColor, winnerStoneColor: StoneColor, newPoint: Point)
    fun showThisTurn(nowTurnStoneColor: StoneColor, point: Point?)
}
