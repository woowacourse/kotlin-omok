package omok.model

import omok.retryWhileNotException

class OmokGame(private val whiteStonePlayer: WhiteStonePlayer, private val blackStonesPlayer: BlackStonePlayer) {
    private var _winner: Player? = null
    val winner: Player
        get() = _winner ?: throw IllegalStateException("게임을 플레이 해야 우승자를 판별할 수 있습니다")

    fun start(
        putStone: (Player) -> Point,
        getResult: () -> Unit,
    ) {
        var currentPlayer: Player = blackStonesPlayer
        while (_winner == null) {
            playerTurn(currentPlayer, putStone)
            getResult.invoke()
            currentPlayer = currentPlayer.next()
        }
    }

    private fun playerTurn(
        currentPlayer: Player,
        putStone: (Player) -> Point,
    ) = retryWhileNotException {
        val point = putStone.invoke(currentPlayer)
        currentPlayer.add(point)
        if (currentPlayer.isWin()) {
            _winner = currentPlayer
        }
    }

    private fun Player.next(): Player {
        if (this.color == Color.BLACK) return whiteStonePlayer
        return blackStonesPlayer
    }
}
