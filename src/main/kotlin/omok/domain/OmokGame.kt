package omok.domain

class OmokGame {
    fun play(getPoint: (String, OmokPoint?) -> OmokPoint, output: (OmokBoard) -> Unit) {
        var playingOmokBoard = OmokBoard()
        var point: OmokPoint? = null
        while (true) {
            point = getPoint(playingOmokBoard.stoneState.korean, point)
            playingOmokBoard = playNext(playingOmokBoard, point)
            output(playingOmokBoard)
        }
    }

    fun playNext(omokBoard: OmokBoard, point: OmokPoint): OmokBoard {
        return runCatching { omokBoard.placeStone(point) }
            .getOrElse { omokBoard }
    }
}
