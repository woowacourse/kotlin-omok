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

    fun playTurn(
        player: Player,
        coordinate: Coordinate,
    ): StoneState {
        val stoneState =
            player.playTurn(
                board,
                coordinate,
            )
        checkWin(player)
        return stoneState
    }
}
