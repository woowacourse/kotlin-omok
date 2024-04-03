package woowacourse.omok.model

interface GameEventListener {
    fun onForbiddenStone(state: StoneState)

    fun onGameEnd(winner: Color)
}
