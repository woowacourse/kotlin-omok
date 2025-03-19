class PlayingBoard(
    val board: OmokBoard = OmokBoard.create(),
) {
    fun placeStone(playerStone: PlayerStone): PlaceResult = placeResult(playerStone)

    private fun placeResult(playerStone: PlayerStone): PlaceResult {
        when (val result = AlreadyExistRule().canPlace(board.value, playerStone)) {
            PlaceResult.Success.Progress -> {
                board.value
                    .find { it.position == playerStone.position }
                    ?.updateState(playerStone.color)
                return result
            }

            else -> return result
        }
    }
}
