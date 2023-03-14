package omok.domain

class OmokBoard(val yLine: YLine) {
    fun placeStone(point: OmokPoint, stoneState: StoneState): OmokBoard {
        return OmokBoard(yLine.placeStone(point, stoneState))
    }

    operator fun get(yCoordinate: YCoordinate) = yLine[yCoordinate]
}
