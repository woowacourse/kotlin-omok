package omok.model

class Players(val whiteStonePlayer: WhiteStonePlayer, val blackStonePlayer: BlackStonePlayer) {
    private var _winner: Player? = null
    val winner: Player
        get() = _winner ?: throw IllegalStateException("게임을 플레이 해야 우승자를 판별할 수 있습니다")

    fun playGame(putStone: (Player) -> Point) {
        var currentPlayer: Player = blackStonePlayer
        while (_winner != null) {
            playerTurn(currentPlayer, putStone)
            currentPlayer = currentPlayer.next()
        }
    }

    private fun playerTurn(
        currentPlayer: Player,
        putStone: (Player) -> Point,
    ) {
        currentPlayer.add(putStone(currentPlayer))
        if (currentPlayer.checkContinuity()) {
            _winner = currentPlayer
        }
    }

    private fun Player.next(): Player {
        if (this.color == Color.BLACK) return whiteStonePlayer
        return blackStonePlayer
    }
}
