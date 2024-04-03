package woowacourse.omok.model

interface GameEventListener {
    fun onFailToPlaceStone(state: StoneState)

    fun onGameEnd(winner: Color)
}
