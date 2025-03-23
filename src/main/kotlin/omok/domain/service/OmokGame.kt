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
            updateTurnResult(point, event::onFailToAddStone)
            if (omokBoard.isOmok(point)) {
                event.onFinishedGame(stone)
                break
            }
            stone = stone.toggle()
        }
    }

    private fun updateTurnResult(
        point: Point,
        onFailToAddStone: (String?) -> Unit,
    ) {
        omokBoard.addStone(point).recover { onFailToAddStone(it) }
        latestStone = latestStone.saveLatestStone(point)
    }
}
