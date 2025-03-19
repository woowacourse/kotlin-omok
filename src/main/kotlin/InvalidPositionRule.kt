class InvalidPositionRule : OmokRule {
    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.value.find { it.position == playerStone.position } != null) {
            PlaceResult.Success.Progress
        } else {
            PlaceResult.Failure.InvalidPosition
        }
}
