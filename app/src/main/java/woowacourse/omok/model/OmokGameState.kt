package omok.model

import omok.model.entity.Point
import omok.model.turn.BlackTurn
import omok.model.turn.Turn

class OmokGameState(
    val turn: Turn = BlackTurn(Board()),
) {
    fun run(point: Point): OmokGameState = OmokGameState(turn.nextTurn(point))

    fun runMultiple(points: List<Point>): OmokGameState {
        var acc: Turn = turn
        points.forEach{ acc = acc.nextTurn(it) }
        return OmokGameState(acc)
    }


    fun isFinished() = turn.isWin() || turn.isDraw()
}
