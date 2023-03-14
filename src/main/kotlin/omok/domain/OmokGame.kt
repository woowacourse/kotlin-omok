package omok.domain

class OmokGame {
    fun playBlack(omokBoard: OmokBoard, xCoordinate: XCoordinate, yCoordinate: YCoordinate): OmokBoard {
        return runCatching { omokBoard.placeStone(xCoordinate, yCoordinate, StoneState.BLACK) }
            .getOrElse { omokBoard }
    }

    fun playWhite(omokBoard: OmokBoard, xCoordinate: XCoordinate, yCoordinate: YCoordinate): OmokBoard {
        return runCatching { omokBoard.placeStone(xCoordinate, yCoordinate, StoneState.WHITE) }
            .getOrElse { omokBoard }
    }
}
