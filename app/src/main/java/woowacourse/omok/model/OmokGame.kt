package woowacourse.omok.model

class OmokGame(
    val board: Board,
) {
    private var isRunning = true

    fun isRunning(): Boolean {
        return isRunning
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
        onPutStone: () -> Unit
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
