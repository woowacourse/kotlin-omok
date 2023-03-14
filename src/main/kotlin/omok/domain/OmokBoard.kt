package omok.domain

class OmokBoard(val yLine: YLine) {
    fun placeStone(xCoordinate: XCoordinate, yCoordinate: YCoordinate, stoneState: StoneState): OmokBoard {
        return OmokBoard(yLine.placeStone(xCoordinate, yCoordinate, stoneState))
    }

    operator fun get(yCoordinate: YCoordinate) = yLine[yCoordinate]
}
