class AlreadyExistRule : OmokRule {
    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.value.find { it.position == playerStone.position }?.state == PointState.EMPTY) {
            PlaceResult.Success.Progress
        } else {
            PlaceResult.Failure.AlreadyExist
        }
}
