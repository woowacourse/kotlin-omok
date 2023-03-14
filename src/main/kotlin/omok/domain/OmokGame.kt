package omok.domain

class OmokGame {
    fun playBlack(omokBoard: OmokBoard, point: OmokPoint): OmokBoard {
        return runCatching { omokBoard.placeStone(point, StoneState.BLACK) }
            .getOrElse { omokBoard }
    }

    fun playWhite(omokBoard: OmokBoard, point: OmokPoint): OmokBoard {
        return runCatching { omokBoard.placeStone(point, StoneState.WHITE) }
            .getOrElse { omokBoard }
    }
}
