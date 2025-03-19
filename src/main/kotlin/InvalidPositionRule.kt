class InvalidPositionRule : OmokRule {
    override fun canPlace(
        omokBoard: List<Point>,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find { it.position == playerStone.position } != null) {
            PlaceResult.Success.Progress
        } else {
            PlaceResult.Failure.InvalidPosition
        }
}
