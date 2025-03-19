class PlayingBoard(
    val board: OmokBoard = OmokBoard.create(),
) {
    fun placeStone(playerStone: PlayerStone): PlaceResult = placeResult(playerStone)

    private fun placeResult(playerStone: PlayerStone): PlaceResult {
        TODO()
    }

    companion object {
    }
}
