package omok.model

class OmokGame(
    val board: Board,
) {
    private var isRunning = true

    fun isRunning(): Boolean {
        return isRunning
    }

    private fun canFindOmokPlayer(player: Player) {
        if (player.isWin) {
            changeState()
        }
    }

    private fun changeState() {
        isRunning = false
    }

    fun playTurn(
        player: Player,
        coordinate: Coordinate,
    ): Boolean {
        val isPutStone =
            player.playTurn(
                board,
                coordinate,
            )
        canFindOmokPlayer(player)
        return isPutStone
    }
}
