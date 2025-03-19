class OmokGame(
    private val playingBoard: PlayingBoard,
) {
    fun start(
        getPosition: (StoneColor) -> Position,
        onStonePlaced: (PlaceResult) -> Unit,
    ) {
        var stoneColor = StoneColor.BLACK

        while (true) {
            val playerStone = PlayerStone(stoneColor, getPosition(stoneColor))
            val placeResult = playingBoard.placeStone(playerStone)
            onStonePlaced(placeResult)

            when (placeResult) {
                is PlaceResult.Success.Finish -> break
                else -> {
                    stoneColor = stoneColor.reverse()
                    continue
                }
            }
        }
    }
}
