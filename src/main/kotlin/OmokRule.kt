interface OmokRule {
    fun canPlace(
        omokBoard: List<Point>,
        playerStone: PlayerStone,
    ): PlaceResult
}
