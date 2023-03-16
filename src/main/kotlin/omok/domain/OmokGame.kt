package omok.domain

import omok.domain.state.BlackStoneState
import omok.domain.state.StoneState

class OmokGame {
    fun play(getPoint: (String, OmokPoint?) -> OmokPoint, output: (OmokBoard) -> Unit) {
        var playingOmokBoard = OmokBoard()
        var point: OmokPoint? = null
        var nextStone: StoneState = BlackStoneState
        while (true) {
            point = getPoint(nextStone.korean, point)
            if (nextStone.checkForbidden(playingOmokBoard, point)) {
                playingOmokBoard = playNext(playingOmokBoard, point, nextStone)
                nextStone = nextStone.next()
            }
            output(playingOmokBoard)
        }
    }

    fun playNext(omokBoard: OmokBoard, point: OmokPoint, stoneState: StoneState): OmokBoard {
        return runCatching { omokBoard.placeStone(point, stoneState) }
            .getOrElse { omokBoard }
    }
}
