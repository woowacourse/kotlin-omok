class DrawRule : OmokRule {
    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.value.all { it.state != PointState.EMPTY }) {
            PlaceResult.Success.Finish(GameResult.DRAW)
        } else {
            PlaceResult.Success.Progress
        }
}
