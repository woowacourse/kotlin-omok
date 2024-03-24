package omok.model

interface BoardInterface {
    fun placeStone(
        coordinate: Coordinate,
        positionType: PositionType,
    )

    fun setupBoard(current: PositionType)
}
