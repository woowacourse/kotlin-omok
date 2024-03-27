package omok.model

import omok.retryWhileNotException

class Players(private val whiteStonePlayer: WhiteStones, private val blackStonesPlayer: BlackStones) {
    private var _winner: Stones? = null
    val winner: Stones
        get() = _winner ?: throw IllegalStateException("게임을 플레이 해야 우승자를 판별할 수 있습니다")

    fun playGame(
        putStone: (Stones) -> Point,
        getResult: () -> Unit,
    ) {
        var currentStones: Stones = blackStonesPlayer
        while (_winner == null) {
            playerTurn(currentStones, putStone)
            getResult.invoke()
            currentStones = currentStones.next()
        }
    }

    private fun playerTurn(
        currentStones: Stones,
        putStone: (Stones) -> Point,
    ) = retryWhileNotException {
        val point = putStone.invoke(currentStones)
        currentStones.add(point)
        if (currentStones.isWin()) {
            _winner = currentStones
        }
    }

    private fun Stones.next(): Stones {
        if (this.color == Color.BLACK) return whiteStonePlayer
        return blackStonesPlayer
    }
}
