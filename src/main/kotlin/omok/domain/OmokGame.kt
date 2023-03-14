package omok.domain

class OmokGame {
    fun play(getPoint: (String, OmokPoint?) -> OmokPoint, output: (OmokBoard) -> Unit) {
        var playingOmokBoard = OmokBoard()
        var point: OmokPoint? = null
        while (true) {
            point = getPoint("흑", point)
            playingOmokBoard = playBlack(playingOmokBoard, point)
            output(playingOmokBoard)
            point = getPoint("백", point)
            playingOmokBoard = playWhite(playingOmokBoard, point)
            output(playingOmokBoard)
        }
    }

    fun playBlack(omokBoard: OmokBoard, point: OmokPoint): OmokBoard {
        return runCatching { omokBoard.placeStone(point, StoneState.BLACK) }
            .getOrElse { omokBoard }
    }

    fun playWhite(omokBoard: OmokBoard, point: OmokPoint): OmokBoard {
        return runCatching { omokBoard.placeStone(point, StoneState.WHITE) }
            .getOrElse { omokBoard }
    }
}
