package omok.domain

interface Rule {
    fun isInvalid(
        stones: Stones,
        lastPlacedStone: Stone,
        playingBoard: Array<Array<StoneType>>,
    ): Boolean

    fun isValidPosition(
        stone: Stone,
        stones: Stones,
        playingBoard: Array<Array<StoneType>>,
    ): Boolean
}
