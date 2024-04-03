package woowacourse.omok.game.model

interface Rule {
    fun isInvalid(
        size: Int,
        stones: Stones,
        lastPlacedStone: Stone,
    ): Boolean

    fun resetCustomBoard()
}
