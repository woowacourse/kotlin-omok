package woowacourse.omok.model

interface GameEventListener {
    fun onForbiddenStone()

    fun onGameEnd(winner: Color)
}
