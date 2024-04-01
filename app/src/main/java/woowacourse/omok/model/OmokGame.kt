package woowacourse.omok.model

class OmokGame(
    val board: Board,
) {
    private var isRunning = true
    private val players = listOf(Player(Color.BLACK), Player(Color.WHITE))

    fun isRunning(): Boolean {
        return isRunning
    }

    fun getCurrentPlayer(): Player {
        val currentPlayerIndex = board.stones.stones.size
        return players[currentPlayerIndex % players.size]
    }

    private fun checkWin(player: Player) {
        if (player.isWin) {
            changeState()
        }
    }

    private fun changeState() {
        isRunning = false
    }

    private fun checkPutStone(stoneState: StoneState): Boolean {
        return when (stoneState) {
            StoneState.SuccessfulPlaced -> isRunning
            else -> false
        }
    }

    fun playTurn(
        player: Player,
        coordinate: Coordinate,
        onPutStone: () -> Unit,
    ): StoneState {
        val stoneState =
            player.playTurn(
                board,
                coordinate,
            )
        if (checkPutStone(stoneState)) {
            onPutStone()
            checkWin(player)
        }
        return stoneState
    }
}
