package omok.domain.service

import omok.controller.event.GameEventListener
import omok.domain.board.OmokBoard
import omok.domain.point.Point
import omok.domain.stone.LatestStone
import omok.domain.stone.StoneColor
import omok.exception.recover

class OmokGame(private val omokBoard: OmokBoard) {
    var latestStone: LatestStone = LatestStone("")
        private set

    fun startGame(event: GameEventListener) {
        var stone = StoneColor.BLACK

        while (omokBoard.isNotFull()) {
            val point = event.onCompleteInputPoint(stone, omokBoard.toMatrix())
            updateTurnResult(point, event, stone)
            if (omokBoard.isOmok(point)) {
                event.onFinishedGame(stone)
                break
            }
            stone = stone.toggle()
        }
    }

    private fun updateTurnResult(
        point: Point,
        event: GameEventListener,
        stone: StoneColor,
    ) {
        omokBoard.addStone(point).recover {
            event.onFailToAddStone(it)
            val newPoint = event.onCompleteInputPoint(stone, omokBoard.toMatrix())
            updateTurnResult(newPoint, event, stone)
        }
        latestStone = latestStone.saveLatestStone(point)
    }
}
