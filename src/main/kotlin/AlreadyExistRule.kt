class AlreadyExistRule : OmokRule {
    override fun canPlace(
        omokBoard: List<Point>,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find { it.position == playerStone.position }?.state == PointState.EMPTY) {
            PlaceResult.Success.Progress
        } else {
            PlaceResult.Failure.AlreadyExist
        }
}
