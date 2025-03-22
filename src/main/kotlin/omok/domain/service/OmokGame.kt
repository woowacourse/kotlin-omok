package omok.domain.service

import omok.domain.board.BoardStatus
import omok.domain.board.OmokBoard
import omok.domain.point.Point
import omok.domain.stone.LatestStone
import omok.domain.stone.StoneColor
import omok.exception.recover

class OmokGame(val omokBoard: OmokBoard) {
    var latestStone: LatestStone = LatestStone("")
        private set

    fun startGame(
        onCompleteInputPoint: (StoneColor, List<List<BoardStatus>>) -> Point,
        onFinishedGame: (StoneColor) -> Unit,
        onFailToAddStone: (String?) -> Unit,
    ) {
        var stone = StoneColor.BLACK
        while (omokBoard.isNotFull()) {
            val point = onCompleteInputPoint(stone, omokBoard.toMatrix())
            updateTurnResult(point, onFailToAddStone)
            if (omokBoard.isOmok(point)) {
                onFinishedGame(stone)
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
