package omok.model

class OmokGame(
    val player: Pair<Player, Player>,
    val board: Board,
) {
    private var currentState: State = State.Running

    fun isRunning(): Boolean {
        return currentState == State.Running
    }

    private fun changeState() {
        if (getWinnerColor() != null) {
            currentState = State.Finished
        }
    }

    private fun getWinnerColor(): Color? {
        if (player.first.isWin) return player.first.color
        if (player.second.isWin) return player.second.color
        return null
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
        changeState()
        return isPutStone
    }
}
