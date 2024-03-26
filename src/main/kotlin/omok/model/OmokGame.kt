package omok.model

class OmokGame(
    val board: Board,
) {
    private var currentState: State = State.Running

    fun isRunning(): Boolean {
        return currentState == State.Running
    }

    private fun canFindOmokPlayer(player: Player) {
        if (player.isWin) {
            changeState()
        }
    }

    private fun changeState() {
        currentState = State.Finished
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
