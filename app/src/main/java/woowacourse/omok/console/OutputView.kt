package woowacourse.omok.console

import domain.point.Point
import domain.stone.StoneColor

interface OutputView {
    fun startGame()
    fun drawStone(lastStoneColor: StoneColor, newPoint: Point)
    fun showPutFailed()
    fun showResult(lastStoneColor: StoneColor, winnerStoneColor: StoneColor, newPoint: Point)
    fun showCurrentTurnColor(curStoneColor: StoneColor, point: Point?)
}
